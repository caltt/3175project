## Activity & Fragment

* **Components**

  * **Activity**

    * File: 	**MainActivity.java**

      

    * Only one main activity that works as app's main page and container for sub-fragments

      

  * **Base Fragment**

    * File: 	**BaseFragment.java**

      

    * The base class of all other fragments

    * Holds the reference of some globally accessed variables so that no need to fetch them in each fragments

      (e.g currently logged in user id, currently selected transaction id, DB & shared preference related components, ...)

      

  * **Fragment**

    * File: 	**MainFragment.java** and others

    

    * Basic unit of page with specific features
    * **Can be** **considered as activities** 

    ​	

* **How they work**

  * This special structure is required by Jetpack's Navigation component. The only activity holds a **NavHostFragment** which decide which fragment should be displayed (see Navigation part)

  * **It should be possible to convert it to a normal multi-activity structure.**

    

## DB

* **Components**

  

  * **Room Database**	

    * File: 	/java/package/db/**A3175Database.java**

    

    * The definition of the DB 
    * Contains entities (see below), converters (see below) and create DB method
    * When creating, some initial data is inserted (like admin account and some categories)	

    

  * **Entity** 	

    - File:	/java/package/db/**User.java** and all others with similar names	

    

    - Specific classes for tables. Each table has its entity class

    - The fields in each class are exactly the same with its corresponding table

    - Basic unit for retrieved data 

    - Have getter & setter for access & change

    - Pre-fill data methods are also defined here

      

  * **Converter**

    * File: 	/java/package/utils/**Converter.java**

      

    * Used to convert more complex object type to basic data types that SQLite supports

    * e.g convert a LocalDate (used for date) / BigDecimal (used for money) variable to int / long and vice versa

      

  * **DAO**

    - File: 	/java/package/db/**BaseDao.java**

      ​			/java/package/db/**UserDao.Java** and all others with similar names

    

    - Have INSERT / UPDATE / DELETE / SELECT methods that directly interact with DB

    - When inserting / updating / deleting, arguments are of corresponding entity type.

      ​	(e.g UserDao's insert() method accepts User type arguments)

    * When selecting, different types can be returned: 

      1. Entity type: for selecting a single record (not for displaying)
      2. List<T>: for selecting a series of records (not for displaying)
      3. LiveData<T>: for selecting single or multiple (for displaying in like a list,see below)

      

  * ~~**Repository**~~

    - File: 	/java/package/db/**A3175Repository.java**

    

    - A middle layer between DAO and ViewModel (looks a bit redundant & useless, just ignore it)

  ​		

  * **ViewModel**

    - File: 	/java/package/db/**UserViewModel.java** and all others with similar names	

      

    - Used for fragments or some other classes to interact with DB

    - When trying to interact with DB, only methods in ViewModels are called, DAOs and repository are at a base level

      

  * **LiveData**

    * A special return type for SELECT method

    * Its purpose is that when the data is changed, the UI (like the list of data) will automatically update and make sure what is shown is always the latest data (see adapter below)

      

* **How they work**

  ​	DB operations mostly happen in fragments. Here are 3 common scenarios:

  1. Create a record
     * Gather input from editText, then create a Entity object (like User / Transaction / ...)
     * Call the insert() method from corresponding viewmodel (userViewModel / transactionViewModel / ...)
     * The viewModel will then call the DAO to access the DB and get results
  2. Fetch a record by id, edit something, then update it
     * Get the id (usually got from the list item which is actually the Entity type)
     * Call the selectById() method in the viewModel to get the Entity object, use setters to edit, then call update() in the viewModel 
  3. Fetch multiple records and display them in a list

     * Call the select() method in the viewModel to get a LiveData object
     * Make the liveData reference the list's adapter, so when there is any change, the list will be updated.

  **This part should be possible to be converted to a regular implementation.**



## UI

* **Components**

  * **RecyclerView**

    * Where to find:	where lists of data are displayed

      

    * The view for displaying multiple rows of data from DB (an advanced **ListView**)

    * Needs a adapter which accepts a list of data to know what to display (see below)

    * **Swipe to delete** feature is attached to it

      

  * **ListAdapter**

    * File: 	/java/package/db/**UserAdapter.java** and similar files

      

    * The mediator between data and view

    * Accept a list of data and bind it to the view.

    * Need a view holder to know each part of data should be put in which part of the view (see below)

    * Items on the list can be interactable. **Click to edit** feature is implemented here

      

  * **ViewHolder**

    * File:	/java/package/db/**UserViewHolder.java** and similar files

      

    * References all like textViews on the item cell's layout, so the adapter knows where to display which part.

      

  * **BottomNavigationView**

    * Where to find:	**MainFragment**

    * File:	/res/menu/**bottom_nav_bar**.xml

      

    * Lets the content of MainFragment switch between Expense Tracker & Big Expense Planner

    * ...

      

  * **ViewPager**

    * Where to find:	**ReportFragment**

      

    * Shows tabs at the top and clicking each tab can lead to different fragments which show text / line chart / pie chart respectively

    * ...

    

  * **Menu**

    * Where to find:	**MainFragment**

    * File:	/res/menu/**main_menu.xml**

      

    * 3 dots in the top right corner of main page

    * Menu items lead to fragments for different  purposes

    

* **How they work**

  ​	TODO

  

## Navigation

* **Components**

  * **Nav Graph**

    * File:	 /res/layout/navigation/**nav_global.xml**

      ​				For all fragments of the app

      ​			/res/layout/navigation/**nav_bottom.xml**

      ​				Only for the two fragments from bottom navigation bar 

  * **Nav controller**

    * Where to find:	**BaseFragment** & anywhere that navigation is needed

      

  

* **How it works**

  * The nav graph contains all fragments and defines all possible navigation paths among fragments. These paths are called "**action**" 
  * When need to go to another page or return to previous page, tell the navController what **action** should be taken, or just go back
  * They are like the **Intent** of the navigation between activities, and **arguments** can be passed from the source to the destination.

  * **It looks like this part can use regular activity switch**

  ​	

  

  
