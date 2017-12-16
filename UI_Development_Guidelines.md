**UI Development and Sharing Repository Guidelines**

The link to repository [https://github.com/LivelyGig/ProductWebUI.git](https://github.com/LivelyGig/ProductWebUI.git) . There will be separate directory structure within "ProductWebUI" repository for each Synereo and LIvelyGig UI. The following guideline must be followed:

**Repository Structure:**



1.  Synereo related UI must be checked in within "sclient/src/main" folder structure and all LivelyGig related UI items must be checked in within "client/src/main" folder. Individual item would include:
*   Artwork i.e. images or specific fonts
*   Stylesheet (CSS) related items
*   Specific libraries not used by other UI apps and please make sure to read the steps below before using new libraries 
1.  All shared component between Synereo and LivelyGig must be placed in "shared/main/scala/shared" folder structure. This includes:
*   JQuery calls to server i.e. API calls related methods
*   Conversion of JSON messages between the client and the server i.e. parser mechanisms
*   Common labels for the messages
*   Other common utilities i.e. date conversion for international users etc

**Branches**



*   Master		Currently (March 2016) the dev branch for LivelyGig and for integration
*   Synereo	Currently (March 2016) the dev branch for Synereo

	Additional branches will be added in the future, e.g. for release.

**Using New Library from Internet:**



1.  If planning to introduce new library to provide a certain functionality to UI, please do the following:
*   Consult with other UI team members if there is any impact and see if they can also use it
*   Add this new library to the [list](https://docs.google.com/spreadsheets/d/1g558QR2_UHU7g9mD7GGwxD-OcPrKNbHmEXYMhQG-lKk/edit#gid=1699370142) 
*   Please read the licensing requirement (very important)
*   Get the **approval** from Ed/Navneet before using it in the code (very important)

**Check-in Process:**



1.  Any changes within shared folder (adding new file, deleting a file or updating existing file) must be consulted with other UI team member(s) before checking in into the repository (or branch)
1.  Send a pull request to Shubham/Navneet if making any changes to shared components
1.  If changes are specific to UI that you are responsible for, no need to confirm with other UI team members.
1.  If making massive changes, always consult with other team members 

**Coding:**



1.  Please follow best practices of the functional programing and few of them are listed below
*   Do not use/match a wildcard pattern _ at top level
*   For compilation, please appropriate flag(s) to display the error rather than warning
*   Keep function small i.e. no 200 lines of code in a function and try to refactor the your function after writing it i.e. see if you can improve performance and memory footprint
*   Try to use explicit type with your function return or variables
*   Document functions signature etc. this will help to create API documentation
*   Use exception handling blocks well so that the application don't break and make whole system unusable 
