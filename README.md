Note: 
The solution code of this project is from scratch based on the following codelabs:
1. Tutorial with provided solution step by step -
   https://developer.android.com/codelabs/basic-android-kotlin-compose-getting-data-internet
3. Project with provided solution code at the end -
   https://developer.android.com/codelabs/basic-android-kotlin-compose-practice-amphibians-app




Project description:    
You're going to build an app on your own that displays a list of books with images from the Google Books API, using coroutines, Retrofit and Kotlin Serialization. The app is expected to do the following:    
1. Make a request to the Google Books API using Retrofit.    
2. Parse the response.    
3. Display asynchronously downloaded images of the books along with their titles in a vertical grid.    
4. Implement best practices, separating the UI and data layer, by using a repository.    
The exact layout and books displayed by the app is up to you. You learn more about how to retrieve the book data in the following sections.    
    
Plan your UI:    
Because you're using a scrolling grid of images, you need to load multiple images simultaneously onscreen. After you've obtained the URL of the image, you can use the AsyncImage composable provided by the Coil library to download the data in the background. Where possible, be sure to indicate to users when your app is using the network.    
    
Plan the network layer:    
For the Bookshelf app, the data layer needs to do the following three things:    
1. Create a Retrofit service to get data from the Google Books API.    
2. Add methods for the service to get a list of books and get information about a specific book.    
3. Use kotlin serialization to extract meaningful data from the JSON response returned by the API.

- Search for books:    
The Google Books API provides a method that returns a list of books based on a particular search term, as described in Using the API.
For example, this URL returns search results for the term "jazz history" (https://www.googleapis.com/books/v1/volumes?q=jazz+history).
There are several query parameters to filter your search. For the Bookshelf app, the q parameter (short for query) is sufficient.
The documentation also shows the expected JSON response. For the Bookshelf app, you need to extract the book's id.
- Request info for a specific book:    
You need to make a request to get info on a specific book. This endpoint takes the id you extracted from the previous response.
(https://www.googleapis.com/books/v1/volumes/<volume_id>).
You can find thumbnail links in the imageLinks object in the volumeInfo object. For this app, the images you want to download are under the thumbnail key.
- Download book thumbnails:    
After you have the thumbnail URL, it can be provided to the AsyncImage composable in each grid item.    
Warning: You need to replace http with https in the URLs for the images to show. You can do this using the String.replace() method.
- Design for testability:    
In addition to the networking concepts, you also learned how to refactor an app to use a repository class for the data layer. For this app, you should design with testability in mind, using a repository to easily swap data sources with dependency injection.
  1. Include a repository interface for the books service.
  2. Implement a repository class that accesses the Retrofit service.    

  Book data needs to be retrieved from the network using a repository, letting you easily swap data sources with dependency injection.
