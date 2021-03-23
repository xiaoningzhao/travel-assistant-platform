# User Service
API doc at [User Service API](https://xiaoningzhao.github.io/travel-assistant-platform/api/user-service-api.html)

## Main Interface
Retrieve user basic information  
URL:  
``/api/user/profile/basic/{userId}  ``  

Output:  
* Success  
avatarUrl could be "" if not set by user.


    {
        "id": 1,
        "email": "zhao@abc.com",
        "firstName": "Xiaoning",
        "lastName": "Zhao",
        "avatarUrl": "/image/1/avatar/eb56de69-4ce7-49da-b19d-60c89de3639d.jpg"
    }


* Failed


    {   
        "timestamp": "2021-03-23T10:01:46.866",  
        "status": 404,  
        "title": "NotFoundException",  
        "message": "User does not exist."
    } 


