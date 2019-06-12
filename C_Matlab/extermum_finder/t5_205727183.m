%question 1 - ploting function to see there is only 1 zero
x = linspace(0,1.4,1000000); % initialize vector x
y = 0.2 + sin(3.*(x.^2)); % initialize vector y
figure(1);
plot(x,y);
text(1,1,'this is the original function');

%question 1 - finding the zero with the dividing by half method
a = 0;
b = 1.4;
c = (a+b)/2;
epsilon  = 0.00001;

iterationCounter = 0; 
epsilonDrawer = zeros(10000);

s = sprintf('n       xa       f(xa)       xb       f(xb)       xc       f(xc)      epsilon');
display(s);

while (abs(a-c) > epsilon)
    iterationCounter = iterationCounter + 1;
    epsilonDrawer(1, iterationCounter) = abs(a-c);
    fa = 0.2 + sin(3*(a^2));
    fb = 0.2 + sin(3*(b^2));
    fc = 0.2 + sin(3*(c^2));
    
    s = sprintf('%f  %f  %f  %f  %f  %f  %f  %f',iterationCounter, a, fa, b, fb, c, fc, abs(a-c));
    display(s);
    if(fa*fc > 0)%checking if the same sign
        a = c;
    else
        b = c;
    end
    c = (a+b)/2;
end

n = linspace(1,iterationCounter,iterationCounter);
newEpsilonDrawer = zeros(1,iterationCounter);
for m = 1:iterationCounter
    newEpsilonDrawer(1,m) = epsilonDrawer(1,m);
end
figure(2);
plot(n,newEpsilonDrawer);
text(8,0.2,'this is epsilon as function of n');

%and now for the linear fit:
newN = zeros(1,iterationCounter);
for m =1:iterationCounter
newN(1,m) = 1/2^(n(1,m));
end
    
p = polyfit(newN,newEpsilonDrawer,1);
linValue = zeros(1,iterationCounter);
for m = 1: iterationCounter
    linValue(1,m) = polyval(p,n(1,m));
end

figure(3);
plot(n ,linValue);
text(1,20,'a    b');
text(1,19,'1.4     0');
text(1,21,'this is the linear fit:');
s = sprintf('the funtion is zero at %f',c);
disp(s);


%Question 2
%finding the minimum using goldenRatio method
goldenRatio = (3-sqrt(5))/2;
a = 0.1;
c = 1;
b = a + (c-a)*goldenRatio;


while(abs(c-a)>epsilon)
    fa = -sin(4*(a^2));
    fb = -sin(4*(b^2));
    fc = -sin(4*(c^2));
    if( c - b > b - a)
        x = b + (c-a)*(1-2*goldenRatio);
        fx = -sin(4*(x^2));
        if(fb<fx)
            c = x;
        else
            a = b;
            b = x;
        end
    else
        x = b - (c-a)*(1-2*goldenRatio);
        fx = -sin(4*(x^2));
        if(fb<fx)
            a = x;
        else
            c = b;
            b = x;
        end    
    end    

end

s = sprintf('Question 2: the minimum is where x = %f and f(x) = %f',b,-sin(4*(b^2)));
display(s);