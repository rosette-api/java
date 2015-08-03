# rosette-api

Rosette API binding for node.js

## Getting Started
Install the module with: `npm install rosette-api`

```javascript
var rosette-api = require('rosette-api');
```

## Examples
See example directory for more complete examples, but the simplest, an API ping, is below (all other endpoints follow similar patterns):
```javascript
var api = new Api(args.key);
api.ping(function(err, res) {
  if (err) {
    console.log("ERROR! " + err);
  }
  else {
    console.log(res);
  }
});
```
