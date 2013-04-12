%Load Fuzzy Inference System from file
fismat = readfis('MarriageSystemFinal');

%fprintf('Welcome to our Fuzzy Marriage System.\n');
%fprintf('Please enter values less between 1 and 0\n');
PCval = input('Enter a value for Personal Character: ');
ELval = input('Enter a value for Education Level: ');
FSval = input('Enter a value for Financial Standing: ');
Bval = input('Enter a value for Beauty: ');
PHval = input('Enter a value for Previous History: ');

%Evaluates the fuzzy system with the given outputs
input = [PCval; ELval; FSval; Bval; PHval];

display('Marriage compatibility is ');

display(evalfis(input, fismat)*100);

ruleview(fismat);

fuzzy(fismat);

ruleedit(fismat);

mfedit(fismat);

surfview(fismat);

