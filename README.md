# YackeenSolutionsTask

### First :
#### I implemented the splash screen with 3 seconds then go to home screen where i build recycler view that will load data from api
#### and progress Bar till the data be loaded from api and also designed details screen that loaded the rest of data , and designed the NOINTERNET Screen

### Second :
#### implemented the pojo class that contain all data that i will gwt from api 
#### and then implemented the adapter to inflate views 
#### then implemented the Api Interface tat contains GET method 
#### then in the home activity i fetch data with retrofit library and set default data in case that there some data with null value then pass the data to details activity
#### handeled the case when internet is lost by checking in each activity if the netwrok disconnected it will throw him to NOINTERNET activity. 

### third ( Searching ):
#### get all names of recipes in a list and make a search bar and getting name from the user and check if the name exists in the recipesList 
#### put the name in temp list and filter the recycler with the temp list only.

### fourth (Sortig ) :
#### make a query to sort items with calories and another query to sort items with fats after deleting "g" and "kcal" and cast it into int to can sorting it 
#### and if filter the recycler by the query that user select and use local storage like shared preference to store his choice so that each time 
#### he open the app he will see his choice still exists.


