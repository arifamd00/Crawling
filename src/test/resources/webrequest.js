chrome.webRequest.onAuthRequired.addListener(
function handler(details){
 return {'authCredentials': {username: "yourusername", password: "yourpassword"}};
},
{urls:["<all_urls>"]},
['blocking']);