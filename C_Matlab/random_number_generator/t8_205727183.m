%question 1: throwing method-

%generating 100000 numbers uniformly ditributed between -6 and 6:
x = random('unif',-6,6,1,100000);
%for each x, generating y (in accordence with the throwing method):
y = random('unif',0,1,1,100000);% it is obvious that the box between 0 and 1 is surrounding the function

fx = 1./(1.+x.^2);

badIndices = find(y>fx);%holds the indices where the condition doesn't hold

Ntotal = 100000;
Nbad = length(badIndices);
Xtemp = 0;
Ytemp = 0;
FXtemp = 0 ;

disp('the program takes up to one minute to finish. please be patient');

while Nbad > 1
    Xtemp = random('unif',-6,6,1,Nbad);
    Ytemp = random('unif',0,1,1,Nbad);
    FXtemp =  1./(1.+Xtemp.^2);
    i = 1;
    while i<Nbad
        x(1,badIndices(1,i)) = Xtemp(1,i);
        y(1,badIndices(1,i)) = Ytemp(1,i);
        i = i + 1;
    end
    fx = 1./(1.+x.^2);
    badIndices = find(y>fx);
    Ntotal = Ntotal + Nbad;
    Nbad = length(badIndices);
end

efficiency = 100000/Ntotal;
figure(1);
hist(x,50);
title('throw method histogrsm. (efficiency displayed in cmd line)');
xlabel('x');
ylabel('number of times, we draw each x');
disp('efficiency for the throwing method:');
disp(efficiency);

%the inverse method:

%after doing some calculations on paper, i got C1 and C2:
C1 = 0.355707;
C2 = 0.5;
u = random('unif',0,1,1,100000);
xInverse = tan((u-C2)./C1);
figure(2);
hist(xInverse,50);
title('Inverse method histogrsm. (efficiency displayed in cmd line)');
xlabel('x');
ylabel('number of times, we draw each x');
disp('efficiency for the Inverse method:');
disp(1);

%Question 2, the combined method:

uCombined = random('unif',0,1,1,100000);
xCombined = tan((u-C2)./C1);
fxCover = 1./(1.+xCombined.^2);%the function that 'covers' the ditribution function
yCombined = random('unif',0,fxCover,1,100000);
fxCombined = ((cos(xCombined)).^2)./(1.+xCombined.^2);
badCombined = find(yCombined>fxCombined);

NtotalCombined = 100000;
Nbad = length(badCombined);
Xtemp = 0;
Ytemp = 0;
FXtemp = 0 ;

while Nbad > 1
    Utemp = random('unif',0,1,1,Nbad);
    Xtemp = tan((u-C2)./C1);
    FXtemp =  ((cos(xCombined)).^2)./(1.+xCombined.^2);
    j = 1;
    Ytemp = zeros(Nbad);
    while j<length(Nbad)
    Ytemp(1,j) = random('unif',0,FXtemp);
    j = j + 1;
    end
    i = 1;
    while i<Nbad
        xCombined(1,badCombined(1,i)) = Xtemp(1,i);
        yCombined(1,badCombined(1,i)) = Ytemp(1,i);
        i = i + 1;
    end
    fxCombined = ((cos(xCombined)).^2)./(1.+xCombined.^2);
    badCombined = find(yCombined>fxCombined);
    Ntotal = Ntotal + Nbad;
    Nbad = length(badCombined);
end

efficiency = 100000/Ntotal;
figure(3);
hist(xCombined,50);
title('combined method histogrsm. (efficiency displayed in cmd line)');
xlabel('x');
ylabel('number of times, we draw each x');
disp('efficiency for the combined method:');
disp(efficiency);


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

xSpace = linspace(-6,6);
fSpace = 12000.*(((cos(xSpace)).^2)./(1.+xSpace.^2));
fCoverSpace = 12000.*(1./(1.+xSpace.^2));
hold on;
plot(xSpace,fSpace,'r');
hold on;
plot(xSpace,fCoverSpace,'g');