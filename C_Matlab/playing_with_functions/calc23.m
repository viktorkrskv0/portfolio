%input: text file with numbers
%output: 4 graphs of f1 f2 by c and f1 f2 by matlab
s = load('calc23.txt');
x = zeros(100,1);
for m = 1:100
    x(m) = s(m,1);
end
f1 = zeros(100,1);
for m = 1:100
    f1(m) = s(m,2);
end
f2 = zeros(100,1);
for m = 1:100
    f2(m) = s(m,3);
end

figure;
plot(x,f1,'r');
hold on;
plot(x,f2,'b');

figure;
plot(x,x.^2-4.*x+8,'g');
hold on;
plot(x,x.^3+2.*x,'g');
