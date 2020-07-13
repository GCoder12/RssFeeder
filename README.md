# RssFeader

This app displays RSS feeds with a two dimensional recycler view.  The list allows the user to switch between horizontal and vertical sections by selecting the section title bars.  
The feeds are organized into categories which the user can show or hide by toggling a rss feed category.  This will update the list to only show the feeds the user has selected.

Right now there are only hard coded feeds, that come from json files.

Currently this uses a hardcoded web serivce, and a database using Room.  I'm using ROOM as a single source of truth (SSO).  The app follows the MVVM design pattern.  

TODO: Add webservice such as RetroFit to parse real rss feeds and add them.
