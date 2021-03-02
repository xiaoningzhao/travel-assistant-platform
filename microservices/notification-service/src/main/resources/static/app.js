let stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#notification").html("");
}

function connect() {
    const socket = new SockJS('/notification');
    stompClient = Stomp.over(socket);
    const userId = $("#userId").val();
    stompClient.connect({userId: userId}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/notification', function (message) {
            showNotifications(JSON.parse(message.body).timestamp+" #"+JSON.parse(message.body).title +": "+ JSON.parse(message.body).content);
        });

        stompClient.subscribe('/user/' + userId + '/msg', function(message){
            console.log(message);
            showNotifications(JSON.parse(message.body).timestamp+" #"+JSON.parse(message.body).title +": "+ JSON.parse(message.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        const userId = $("#userId").val();
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


function showNotifications(message) {
    $("#notification").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});