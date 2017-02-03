# README #

## What is this repository for? ##

This is where the algorithm lives for matching bigs and littles according to the rules in the AX Constitution. This website outlines the steps needed to download the neccessary code and run the algorithm. Original creator of the algorithm is Kylie Moden, and active AX members will maintain this repository. 

# How do I get set up? 

## First clone the AlphaChiLambdaMatchingSystem repository 
 
This simply means you need to enter the command below into a terminal. On Macs and Windows, you simply use the Terminal application. The path to this application if you have trouble finding it, on Mac the path should be Applications -> Utilities -> Terminal and on Windows, Applications -> Accessories -> Terminal.

```
git clone https://github.com/kjm1425/AlphaChiLambdaMatchingSystem.git
```
Now you have downloaded all the neccessary code.

## Second, we need to set up the algorithm to run.

This sounds complicated but, once again, all you have to do is enter a few simple commands into the terminal. 

First we need to change directories to be inside the folder where all the code is. To do this, enter the command below.
```
cd AlphaChiLambdaMatchingSystem
```
Next we need to run the makefile to set up everything for the algorithm to run. Simply run this make command below.
```
make 
```
You should get this output after running the make command. 

```
javac -d . -classpath .  Match.java 
```

Lastly, you need to ensure that you have the premissions to execute the code. Simply enter this command and you will be good to go. 

```
chmod a+rwx match
```

Now you are ready to run the algorithm.

## Next, we need to get the data

The algorithm uses spreasheets in CSV format in order to run. Thankfully these can be easily created by using a Google form to send out to bigs and littles to fill out with their preferences. Then they can be downloaded in CSV format and used by the algorithm. 

Moving forward, the Littles form/sheet referes to the ones the incoming PC will fill out and make preferences for their bigs. The Bigs form/sheet refers to the ones the active AXs will fill out with their preferences for littles.

### Example Forms
* [Here is an example of the Bigs form in Google forms](https://docs.google.com/forms/d/1WUfnbyiRyeTWUEcPcnCYJwPiJGoIAABUz-r9XWPjMas/edit?usp=sharing)
* [Here is an example of the Littles form in Google forms](https://docs.google.com/forms/d/1U7fk-nYvrbSE6aQLvUSk_4l8THit1qLgaCbBVdI6_qM/edit?usp=sharing)

### Example Spreadsheets
* [Here is an example of the Bigs sheet in CSV format](https://github.com/kjm1425/AlphaChiLambdaMatchingSystem/blob/master/Bigs.csv)
* [Here is an example of the Littles sheet in CSV format](https://github.com/kjm1425/AlphaChiLambdaMatchingSystem/blob/master/Littles.csv)

It is important to emphasize that all spellings must match exactly of the names of the bigs and littles. Any errors here will cause the algorithm to not work properly. Furthermore ensure that you create the forms/sheets exactly as presented here. Any extra columns or extraneous information will mess up the algorithm. 

## Now Run the Algorithm

To do all the matching computations, run the command below using the filenames of the big and little preferences spreadsheets in csv format as described in the previous section. It is easiest to just add the Bigs and Littles sheets to the AlphaChiLambdaMatchingSystem folder yourself using the drag and drop systems on your computer, however if you are verse with computers you can simply enter the entire filepath as shown below. 

```
./match /YourFilepath/NameOfYourBigsSheet.csv /YourFilepath/NameOfYourLittlesSheet.csv

```
There are an example Bigs and Littles csv sheets in the repository, so to test the system and see an example of the algorithm in action, run the following command. Note how there is no need for a filepath as these sheets are in the AlphaChiLambdaMatchingSystem folder. 

```
./match Bigs.csv Littles.csv

```
After running this command, you should get the following output. The result is all of the matches with sections so that you can follow the process of the algorithm. 

```

************************************************
****************  First Matches  ***************

Julia: Ret 
Haley: Andrea 
Sarah: Emily 
Mia: Anne 
Clara: Faith 
Dani: Claire 
Allison: Caitlin  
Ronja: Kat 
Anna: McKenzie 

Unmatched Littles: Kylie Destiny 
Unmatched Bigs: Suzie Emma 

************************************************
***********   Matches of Restricted  ***********

Julia: Ret 
Haley: Andrea 
Sarah: Emily 
Mia: Anne 
Clara: Faith 
Dani: Claire 
Allison: Caitlin  
Emma: Destiny 
Ronja: Kat 
Anna: McKenzie 

Unmatched Littles: Kylie 
Unmatched Bigs: Suzie 

************************************************
***********  First Matches of Twins  ***********

Julia: Ret 
Haley: Andrea 
Sarah: Emily 
Mia: Anne 
Clara: Faith 
Dani: Claire 
Allison: Caitlin  
Emma: Destiny 
Ronja: Kat Kylie 
Anna: McKenzie 

Unmatched Littles: 
Unmatched Bigs: Suzie 

************************************************
*********   Matches of Restricted Twins  *******

Julia: Ret 
Haley: Andrea 
Sarah: Emily 
Mia: Anne 
Clara: Faith 
Dani: Claire 
Allison: Caitlin  
Emma: Destiny 
Ronja: Kat Kylie 
Anna: McKenzie 

Unmatched Littles: 
Unmatched Bigs: Suzie Emma 

******Remaining bigs could not matched by any method - these bigs must be hand matched****
*** Suzie *** /MAY ONLY NEED A TWIN/ Emma ***

***** FORCE MATCH of Destiny *****
```

During the "First Matches" step, all elligible Bigs and Littles are matched with their first pairing. As per the constitution, the algorithm bases this off of the littles preferences first. As you can see in this example, there are multiple girls not matched. This is to demonstrate that the algorithm handles outliers. Here Emma is not matched as she is a restricted big, meaning she has already taken a little previously and thus must only get a little after everyone else is matched with their first. Also in this example, Suzie is not preferred by any little and is thus not matched. The littles Destiny and Kylie are also currently unmatched as all possible first matches have been made.

Next in the "Matches of Restricted" section, any restricted bigs will be matched. In this case, Emma is matched with Destiny. However at the very bottom of the printout we see a note that there was a "FORCE MATCH of Destiny". This occurs when the littles preferences cannot be met so the algorithm looks to see if there is a big prefferring that little. This is super rare, however as it is a force match it is listed in case Recruitment, O-team, and the presidents may want to reverse that decison. Furthermore in the case of Suzie, she does not get a force match as neither Kylie or Destiny were one of her preferences. Suzie's case is even more rare, however if it happens there will be a printout at the very bottom of any unmatched bigs or littles. 

In the following section "First Matches of Twins" occurs directly followed by "Matches of Restricted Twins". These operate the same way as the inital matches but only with those Bigs who marked themselves as taking twins. In this last section the final matches have been made and any remaining issues will be at the bottom. 

### Who do I talk to if I have problems? ###

Repo creator is Kylie Moden - kyliemoden@gmail.com



