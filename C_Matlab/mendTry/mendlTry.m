cR = linspace(-2.5,1.5,400);
cI = linspace(-1.5,1.5,400);
[cR,cI] = meshgrid(cR,cI);
c = complex(cR,cI);
counts = zeros(400);
z1 = zeros(400);
for m = 0:25
       z1=z1.^2+c;
       counts=counts+(abs(z1)<2);
end
imagesc(counts);