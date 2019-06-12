function [m, dm, xcm, dxcm, ngood]=cylint(rho, rmax, xyzmin, xyzmax, n, usehalt)

x = zeros(1,n);
y = zeros(1,n);
z = zeros(1,n);

xcm = zeros(1,3);
dxcm = zeros(1,3);

if usehalt == 0
%getting random numbers between xyzmin and xyzmax
    x = random('unif',xyzmin(1,1),xyzmax(1,1),1,n);
    y = random('unif',xyzmin(1,2),xyzmax(1,2),1,n);
    z = random('unif',xyzmin(1,3),xyzmax(1,3),1,n);
else
%getting halt numbers between xyzmin and xyzmax
    p=haltonset(3);
    temp = net(p,n);
    for k = 1:n
        %of course we need to multiply by the dimension of the problem
        %in order to get not only the numbers between 0 an 1...
        %also notice that the halton set is not good in our case,
        %since it conssists only of positive numbers
        x(1,k) =(xyzmax(1,1)-xyzmin(1,1))*temp(k,1);
        y(1,k) =(xyzmax(1,2)-xyzmin(1,2))*temp(k,2);
        z(1,k) =(xyzmax(1,3)-xyzmin(1,3))*temp(k,3);
    end    
end

fSum = 0;
f2Sum = 0;
ngood = 0;
centerSumX = 0;
centerSum2X = 0;
centerSumY = 0;
centerSum2Y = 0;
centerSumZ = 0;
centerSum2Z = 0;
for i = 1:n
    xt = x(1,i);%the letter t, denotes 'temp'
    yt = y(1,i);
    zt = z(1,i);
    if((xt*xt) + (yt*yt))<=rmax && zt>=xyzmin(1,3) && zt<=xyzmax(1,3)
        %happens only when the point is inside the cylinder
        ngood = ngood + 1;
        f = rho(xt,yt,zt);
        fSum = fSum + f;
        f2Sum = f2Sum + f*f;
        
        centerSumX = centerSumX + xt*f;
        centerSum2X = centerSum2X +xt*xt*f*f;
        
        centerSumY = centerSumY + yt*f;
        centerSum2Y = centerSum2Y +yt*yt*f*f;
        
        centerSumZ = centerSumZ + zt*f;
        centerSum2Z = centerSum2Z +zt*zt*f*f;
    end
end    

fSum = fSum/ngood;
f2Sum = f2Sum/ngood;
centerSumX = centerSumX/ngood;
centerSum2X = centerSum2X/ngood;
centerSumY = centerSumY/ngood;
centerSum2Y = centerSum2Y/ngood;
centerSumZ = centerSumZ/ngood;
centerSum2Z = centerSum2Z/ngood;

V = (xyzmax(1,3) - xyzmin(1,3))*pi*rmax*rmax;
m = V*fSum;
dm = V*sqrt((f2Sum - (fSum*fSum))/ngood);

xcm(1,1) = centerSumX;
dxcm(1,1) = sqrt((centerSum2X - (centerSumX*centerSumX))/ngood);

xcm(1,2) = centerSumY;
dxcm(1,2) = sqrt((centerSum2Y - (centerSumY*centerSumY))/ngood);

xcm(1,3) = centerSumZ;
dxcm(1,3) = sqrt((centerSum2Z - (centerSumZ*centerSumZ))/ngood);

end

