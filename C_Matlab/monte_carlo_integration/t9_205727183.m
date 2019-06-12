%[m, dm, xcm, dxcm, ngood] = cylint(rho, rmax, xyzmin, xyzmax, n, usehalt);
%defining the box
xyzmin = zeros(1,3);
xyzmin(1,1) = -3;
xyzmin(1,2) = -3;
xyzmin(1,3) = 0;

xyzmax = zeros(1,3);
xyzmax(1,1) = 3;
xyzmax(1,2) = 3;
xyzmax(1,3) = 4;

[m, dm, xcm, dxcm, ngood] = cylint(@rho, 3, xyzmin, xyzmax,1000 , 0);

%without using halton set:
disp('without using halton set:');
disp('for n = 1000: ');
disp('m = ');
disp(m);
disp('dm = ');
disp(dm);
disp('xcm = ');
disp(xcm);
disp('dxcm = ');
disp(dxcm);
disp('efficiency = ');
disp(ngood/1000);

[m, dm, xcm, dxcm, ngood] = cylint(@rho, 3, xyzmin, xyzmax,10000 , 0);
disp('for n = 10000: ');
disp('m = ');
disp(m);
disp('dm = ');
disp(dm);
disp('xcm = ');
disp(xcm);
disp('dxcm = ');
disp(dxcm);
disp('efficiency = ');
disp(ngood/10000);

[m, dm, xcm, dxcm, ngood] = cylint(@rho, 3, xyzmin, xyzmax,100000 , 0);
disp('for n = 100000: ');
disp('m = ');
disp(m);
disp('dm = ');
disp(dm);
disp('xcm = ');
disp(xcm);
disp('dxcm = ');
disp(dxcm);
disp('efficiency = ');
disp(ngood/100000);

[m, dm, xcm, dxcm, ngood] = cylint(@rho, 3, xyzmin, xyzmax,1000000 , 0);
disp('for n = 1000000: ');
disp('m = ');
disp(m);
disp('dm = ');
disp(dm);
disp('xcm = ');
disp(xcm);
disp('dxcm = ');
disp(dxcm);
disp('efficiency = ');
disp(ngood/1000000);

%using halton set:
[m, dm, xcm, dxcm, ngood] = cylint(@rho, 3, xyzmin, xyzmax,1000 , 1);
disp('using halton set:');
disp('for n = 1000: ');
disp('m = ');
disp(m);
disp('dm = ');
disp(dm);
disp('xcm = ');
disp(xcm);
disp('dxcm = ');
disp(dxcm);
disp('efficiency = ');
disp(ngood/1000);

[m, dm, xcm, dxcm, ngood] = cylint(@rho, 3, xyzmin, xyzmax,10000 , 1);
disp('for n = 10000: ');
disp('m = ');
disp(m);
disp('dm = ');
disp(dm);
disp('xcm = ');
disp(xcm);
disp('dxcm = ');
disp(dxcm);
disp('efficiency = ');
disp(ngood/10000);

[m, dm, xcm, dxcm, ngood] = cylint(@rho, 3, xyzmin, xyzmax,100000 , 1);
disp('for n = 100000: ');
disp('m = ');
disp(m);
disp('dm = ');
disp(dm);
disp('xcm = ');
disp(xcm);
disp('dxcm = ');
disp(dxcm);
disp('efficiency = ');
disp(ngood/100000);

[m, dm, xcm, dxcm, ngood] = cylint(@rho, 3, xyzmin, xyzmax,1000000 , 1);
disp('for n = 1000000: ');
disp('m = ');
disp(m);
disp('dm = ');
disp(dm);
disp('xcm = ');
disp(xcm);
disp('dxcm = ');
disp(dxcm);
disp('efficiency = ');
disp(ngood/1000000);

%for the smaller box part:
xyzmin(1,1) = 1;
xyzmin(1,2) = -3;
xyzmin(1,3) = -1;

xyzmax(1,1) = 3;
xyzmax(1,2) = 3;
xyzmax(1,3) = 4;

%using random numbers:
disp('for the smaller box part:');

disp('without halton set:');
[m, dm, xcm, dxcm, ngood] = cylint(@rho, 3, xyzmin, xyzmax,1000000 , 0);
disp('for n = 1000000: ');
disp('m = ');
disp(m);
disp('dm = ');
disp(dm);
disp('xcm = ');
disp(xcm);
disp('dxcm = ');
disp(dxcm);
disp('efficiency = ');
disp(ngood/1000000);

disp('with the halton set:');
[m, dm, xcm, dxcm, ngood] = cylint(@rho, 3, xyzmin, xyzmax,1000000 , 1);
disp('for n = 1000000: ');
disp('m = ');
disp(m);
disp('dm = ');
disp(dm);
disp('xcm = ');
disp(xcm);
disp('dxcm = ');
disp(dxcm);
disp('efficiency = ');
disp(ngood/1000000);
