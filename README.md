# City Lookup Service

(Backend developer coding project, inspired by https://github.com/busbud/coding-challenge-backend-c)

## Requirements

Design and build a REST API endpoint that provides auto-complete suggestions
for large cities.

### Specifications

The API takes as input a _required_ input string, and an _optional_ lat/long
specification indicating the location where the query is being performed from.
It returns as output a list of suggested names that match the input characters
sent in; if a lat/long specification is passed in, the list is ranked/sorted
by closest to farthest from the input lat/long coordinates.

It must adhere to the following specifications:

- The endpoint is exposed at `/suggestions`
- The partial (or complete) search term is passed as a querystring parameter `q`
- The caller's location can optionally be supplied via querystring parameters
  `latitude` and `longitude` to help improve relative scores
- The endpoint returns a JSON response with an array of scored suggested matches
    - The suggestions are sorted by descending score
    - Each suggestion has a score between 0 and 1 (inclusive) indicating confidence
      in the suggestion (1 is most confident)
    - Each suggestion has a name which can be used to disambiguate between
      similarly named locations
    - Each suggestion has a latitude and longitude

## Submission Dos and Donts

### DOs
* Do use any popular, modern language and technology stack of your choice. 
* Do ensure your final working project is deployed and accessible on a public
  cloud (Heroku, AWS, GCP, Azure, etc., using their free tiers)
* Do write self-documenting code. The repository must include all pieces that are
  reasonably required for an understanding of how the software can be setup and/or
  run locally, if relevant.
* Do include a `README` with the following:
  * what major design decisions did you have to make
  * why did you choose any frameworks used
  * a working [cURL](https://curl.haxx.se) command to hit your web service
  * a references section listing any _significant_ resources used during development
    (significant StackOverflow questions, articles, books, etc. You do not need to
    include links to questions about language syntax, for e.g.)
* Do implement your solution as you would for a production setting
* Do consider scale during your design

### DONTs
- Don't jump right in if you have any doubts as to whether your choice of language/
  framework might not be suitable for our review (e.g. less common ones such as 
  Clojure, Fortran, Elixir, etc.). Please contact us ahead of time to clarify.


## FAQ

### Can I use a database?

This is left as a design choice as long as you're able to defend your choice. Some
things worth considering are: are we depending upon an unproven technology? or a product
with an attractive free tier but expensive paid service? are we losing fine-grained
control? is the design overcomplicated?

### Can I use an external search system such as ElasticSearch?

See above.

## Sample responses

These responses are meant to provide guidance. The exact values can vary based on 
the data source and scoring algorithm.

**Near match**

    GET /suggestions?q=Londo&latitude=43.70011&longitude=-79.4163

```json
{
  "suggestions": [
    {
      "name": "London, ON, Canada",
      "latitude": "42.98339",
      "longitude": "-81.23304",
      "score": 0.9
    },
    {
      "name": "London, OH, USA",
      "latitude": "39.88645",
      "longitude": "-83.44825",
      "score": 0.5
    },
    {
      "name": "London, KY, USA",
      "latitude": "37.12898",
      "longitude": "-84.08326",
      "score": 0.5
    },
    {
      "name": "Londontowne, MD, USA",
      "latitude": "38.93345",
      "longitude": "-76.54941",
      "score": 0.3
    }
  ]
}
```

**No match**

    GET /suggestions?q=SomeRandomCityInTheMiddleOfNowhere

```json
{
  "suggestions": []
}
```

## References

- Geonames provides city lists Canada and the USA. See
  http://download.geonames.org/export/dump/readme.txt

## Getting Started

Begin by forking this repo, committing to a branch, and providing us your branch
details.
