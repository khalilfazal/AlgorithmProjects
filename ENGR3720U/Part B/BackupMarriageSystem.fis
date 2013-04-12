[System]
Name='MarriageSystem'
Type='mamdani'
Version=2.0
NumInputs=5
NumOutputs=1
NumRules=25
AndMethod='min'
OrMethod='max'
ImpMethod='min'
AggMethod='max'
DefuzzMethod='centroid'

[Input1]
Name='PersonalCharacter'
Range=[0 1]
NumMFs=5
MF1='NotNice':'trimf',[-0.6 0 0.4]
MF2='SomewhatNice':'trimf',[0 0.25 0.5]
MF3='Nice':'trimf',[0.1 0.5 0.9]
MF4='VeryNice':'trimf',[0.5 0.75 1]
MF5='ExtremelyNice':'trimf',[0.6 1 1.4]

[Input2]
Name='EducationLevel'
Range=[0 1]
NumMFs=5
MF1='NoEducation':'trimf',[-0.4 0 0.4]
MF2='HighSchool':'trimf',[0 0.25 0.5]
MF3='DiplomaOrDegree':'trimf',[0.1 0.5 0.9]
MF4='Masters':'trimf',[0.5 0.75 1]
MF5='Doctorate':'trimf',[0.6 1 1.4]

[Input3]
Name='FinancialStanding'
Range=[0 1]
NumMFs=5
MF1='Broke':'trimf',[-0.4 0 0.4]
MF2='Poor':'trimf',[0 0.25 0.5]
MF3='Average':'trimf',[0.1 0.5 0.9]
MF4='Prosperous':'trimf',[0.5 0.75 1]
MF5='Rich':'trimf',[0.6 1 1.4]

[Input4]
Name='Beauty'
Range=[0 1]
NumMFs=5
MF1='Hideous':'trimf',[-0.4 0 0.4]
MF2='Ugly':'trimf',[0 0.25 0.5]
MF3='Average':'trimf',[0.1 0.5 0.9]
MF4='Cute':'trimf',[0.5 0.75 1]
MF5='Beautiful':'trimf',[0.6 1 1.4]

[Input5]
Name='PreviousHistory'
Range=[0 1]
NumMFs=5
MF1='TotalStranger':'trimf',[-0.6 0 0.4]
MF2='Associate':'trimf',[0 0.25 0.5]
MF3='Friend':'trimf',[0.1 0.5 0.9]
MF4='CloseFriend':'trimf',[0.5 0.75 1]
MF5='ChildhoodFriend':'trimf',[0.75 1 1.25]

[Output1]
Name='MarriageScore'
Range=[0 1]
NumMFs=5
MF1='NeverInAMillionYears':'trimf',[-0.4 0 0.4]
MF2='Risky':'trimf',[0 0.25 0.5]
MF3='Potential':'trimf',[0.1 0.5 0.9]
MF4='Promising':'trimf',[0.5 0.75 1]
MF5='Destined':'trimf',[0.6 1 1.4]

[Rules]
1 0 0 0 0, 1 (0.7) : 1
2 0 0 0 0, 2 (0.7) : 1
3 0 0 0 0, 3 (0.7) : 1
4 0 0 0 0, 4 (0.7) : 1
5 0 0 0 0, 5 (0.7) : 1
0 1 0 0 0, 1 (0.5) : 1
0 2 0 0 0, 2 (0.5) : 1
0 3 0 0 0, 3 (0.5) : 1
0 4 0 0 0, 4 (0.5) : 1
0 5 0 0 0, 5 (0.5) : 1
0 0 1 0 0, 1 (0.8) : 1
0 0 2 0 0, 2 (0.8) : 1
0 0 3 0 0, 3 (0.8) : 1
0 0 4 0 0, 4 (0.8) : 1
0 0 5 0 0, 5 (0.8) : 1
0 0 0 1 0, 1 (0.6) : 1
0 0 0 2 0, 2 (0.6) : 1
0 0 0 3 0, 3 (0.6) : 1
0 0 0 4 0, 4 (0.6) : 1
0 0 0 5 0, 5 (0.6) : 1
0 0 0 0 1, 1 (0.3) : 1
0 0 0 0 2, 2 (0.3) : 1
0 0 0 0 3, 3 (0.3) : 1
0 0 0 0 4, 4 (0.3) : 1
0 0 0 0 5, 5 (0.3) : 1
