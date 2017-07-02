files = dir('*.txt'); 
numfiles = length(files);
mydata = cell(1, numfiles);
names = {}
hold on;
for k = 1:numfiles
  myfilename = files(k).name;
  mydata{k} = importdata(myfilename);
  plot(cumsum(nansum(mydata{k}.data)), 'Linewidth',2,'Color', [1/(numfiles*5)*k*5,0,1/(numfiles*5)*k*5])
  names = [names; strrep(myfilename(45:65), '_', ' ')];
end

title('Q-Learning parameter evaluation')
xlabel('Number of steps')
ylabel('Accumulated reward')
legend(names)

hold off; 