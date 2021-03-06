s = load('poli10.txt');
x = zeros(100,1);
for m = 1:100
    x(m) = s(m,1);
end
f1 = zeros(100,1);
for m = 1:100
    f1(m) = s(m,2);
end
df1 = zeros(100,1);
for m = 1:100
    df1(m) = s(m,3);
end
figure
plot(x,(x.*sin(2.*x)).*abs(x-0.75),'b');
hold on;
errorbar(x,f1,df1,'r');

s2 = load('spl.txt');
x2 = zeros(100,1);
for m = 1:100
    x2(m) = s2(m,1);
end
f2 = zeros(100,1);
for m = 1:100
    f1(m) = s2(m,2);
end
figure
plot(x,(x.*sin(2.*x)).*abs(x-0.75),'b');
hold on;
plot(x,f1,'r');
