# Cakes As A Service

## Problem Statement

You are a developer for Cakes-As-A-Service, a personalized cake baking company that allows customers to design their own branded cakes and issue baking requests in bulk. Customer orders come in through a order prep web service and contain a pair of URLs to two arbitrary images -- one representing what the base cake should look like, and one for the brand or logo to represent via the icing on the cake. The web service determines if the images are suitable, generates coordinates to center the logo on the base, and then passes the request on to the kitchen web service with this metadata.

### Task 1 - Service
 Your task is to build a simple HTTP order prep web service that accepts URLs to two rectangular images -- one for the cake base and one for the logo. It must respond as follows:

* If the logo image cannot be fully contained within the base, reject the input. Note that rotation of either image is permitted if that will allow correct fitment.
* If the logo image fits wholly within the base, return information on where within the base image the logo should be inset so that it is centered.

 Your service must obey the following interface:

 `POST /api/design-a-cake`

Request body:

```json
{
	"base": "url to base image",
	"logo": "url to logo image"
}
```

Response body:
`200 OK`

```json
{
	"placement" : {
		// where in "base" should "logo" be centered;
		// the (x,y) coordinates of the base matching
		// the (0,0) of the logo
		"x" : xpos, // x coordinate of the top left
					// *of base image* where logo image
					// sits
		"y" : ypos  // y coordinate of the top left
					// *of base image* where logo image
					// sits
	}
}
```

### Examples

#### Success 1
Here, the base image is 1000x750 and the logo is 128x128.

```json
{
	"base": "http://media.salon.com/2015/04/shutterstock_187788812.jpg",
	"logo": "https://img.utdstc.com/icons/128/lucky-patcher-android.png"
}
```

Response:
`200 OK`

```json
{
	"placement" : {
		"x" : 436,
		"y" : 311
	}
}
```

#### Success 2
Here, the base image is 128x128 and the logo is 1000x750.

```json
{
	"base": "https://img.utdstc.com/icons/128/lucky-patcher-android.png",
	"logo": "http://media.salon.com/2015/04/shutterstock_187788812.jpg"
}
```

Response:
`400 BAD REQUEST`

### Task 2 - Test UI
Your project must include a publicly accessible web page that includes a form we can use to test your service. The design of this form is left to you; there is no requirement for it to look pretty, but is simply an opportunity for you to show us your front-end skills. This form should include fields for a user to provide the necessary input values, a way to submit the request to the service, and a simple way to indicate success or failure of the request.

## Notes
You may use any popular, modern language and technology stack of your choice. If you have any doubts as to whether your choice might be suitable for our review (e.g. less common ones such as Clojure, Fortran, Elixir, etc.), please contact us ahead of time.

* Please write self-documenting code and make it available either via a github (or similar) project, or a ZIP file. The repository must include all pieces that are reasonably required for an understanding of how the software can be setup and/or run.
* Include a `README` with the following:
  * what major design decisions did you have to make * why did you choose the framework you chose
  * a working [cURL](https://curl.haxx.se) command to hit your web service, and a link to the URL to hit the input page as discussed above
  * a references section listing any _significant_ resources used during development (StackOverflow questions, articles, books, etc. You do not need to include links to questions about language syntax, for e.g.)
