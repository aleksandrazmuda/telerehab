// var conn = new WebSocket('ws://localhost:8080/socket'); //creating connection
// //
// //
// // //metoda wysyłania do serwera sygnalizującego - zostanie wykorzystana do przekazania wiadomości
// // function send(message) {
// //     conn.send(JSON.stringify(message));
// // }
// //
// // //konfugurowanie kanału RTCPeerConnection
// // //tu ustawiamy obiekt i włączamy kanał danych (przekazując true)
// // var peerConnection = new RTCPeerConnection(configuration, {
// //     optional : [ {
// //         RtpDataChannels : true
// //     } ]
// // });
// //
// // //W tym przykładzie celem obiektu konfiguracyjnego jest przekazanie serwerów STUN
// // //i TURN oraz innych konfiguracji, omawianych w dalszej części.
// // //W tym przykładzie wystarczy przekazać null.
// //
// //
// // //tworzę kanał do przekazania wiadomości
// // var dataChannel = peerConnection.createDataChannel("dataChannel", { reliable: true });
// //
// //
// // //możemy stworzyć listenery, do nasłuchiwania pewnych eventów na kanale
// // dataChannel.onerror = function(error) {
// //     console.log("Error:", error);
// // };
// // dataChannel.onclose = function() {
// //     console.log("Data channel is closed");
// // };
// //
// // // ESTABLISHING CONNECTION WITH ICE
// // // Następnym krokiem w ustanawianiu połączenia WebRTC są protokoły ICE (Interactive Connection Establishment) i SDP,
// // // w których opisy sesji peerów są wymieniane i akceptowane przez oba peery.
// // // Serwer sygnalizacyjny jest używany do przesyłania tych informacji między peerami.
// // // Obejmuje to szereg kroków, w których klienci wymieniają metadane połączenia za pośrednictwem serwera sygnalizacyjnego.
// //
// // // 1. Creating an offer.
// // // Najpierw tworzymy offer i ustawiamy ją jako lokalny opis peerConnection. Następnie wysyłamy ofertę do drugiego partnera:
// // peerConnection.createOffer(function(offer) {
// //     send({
// //         event : "offer",
// //         data : offer
// //     });
// //     peerConnection.setLocalDescription(offer);
// // }, function(error) {
// //     // Handle error here
// // });
// // //W tym przypadku metoda send wywołuje serwer sygnalizacyjny w celu przekazania informacji o offer
// // //Możemy zaimplementować logikę metody wysyłania w dowolnej technologii po stronie serwera.
// //
// // // 2. Obsługa kandydatów ICE (protokół Interactive Connection Establishment) do wykrywania peerów i ustanawiania połączenia
// // // Kiedy ustawimy lokalny opis na peerConnection to wyzwala event - icecandidate
// // // ten event przesyła candidate do zdalnego peera, tak aby mógł dodać go do zestawu zdalnych candidates
// // // w tym celu tworze listener dla eventu - onicecandidate
// //
// // peerConnection.onicecandidate = function(event) {
// //     if (event.candidate) {
// //         send({
// //             event : "candidate",
// //             data : event.candidate
// //         });
// //     }
// // };
// //
// // // icecandidate jest wyzwaleane ponownie with an empty candidate string when all the candidates are gathered.
// // // taki pusty obiekt candidate też trzeba przekazać do zdalnego peera, aby upewnić się, że zdalny peer wie
// // // że wszystkie obiekty icecandidate są zebrane
// //
// // // 3. Odbieranie ICE candidate.
// // // Zdalny peer, po otrzymaniu kandydata dodaje go do puli kandydatów
// //
// // peerConnection.addIceCandidate(new RTCIceCandidate(candidate));
// //
// // // 4. Otrzymywanie offer
// // // Gdy peer otrzyma ofertę musi ustawić ją jako zdalny opis. Dodatkowo generuje answer i wysyła do inicjującego peera
// //
// // peerConnection.setRemoteDescription(new RTCSessionDescription(offer));
// // peerConnection.createAnswer(function(answer) {
// //     peerConnection.setLocalDescription(answer);
// //     send({
// //         event : "answer",
// //         data : answer
// //     });
// // }, function(error) {
// //     // Handle error here
// // });
// //
// // // 5. Otrzymywanie odpowiedzi
// // // Inicjujący peer otrzymuję answer i ustawia ją na zdalny opis
// //
// // function handleAnswer(answer){
// //     peerConnection.setRemoteDescription(new RTCSessionDescription(answer));
// // }
// // // established connection, teraz można wysyłać i odbierać dane bezpośrednio bez signaling server
// // // za pomocą metody send
// // dataChannel.send("message");
// //
// // // żeby odbierać tworzymy event listenera dla onmessage event
// // dataChannel.onmessage = function(event) {
// //     console.log("Message:", event.data);
// // };
// //
// // // VIDEO AND AUDIO CHANNELS
// // const constraints = {
// //     video: true,audio : true
// // };
// // navigator.mediaDevices.getUserMedia(constraints).
// // then(function(stream) { /* use the stream */ })
// //     .catch(function(err) { /* handle the error */ });
// //
// // // var constraints = {
// // //     video : {
// // //         frameRate : {
// // //             ideal : 10,
// // //             max : 15
// // //         },
// // //         width : 1280,
// // //         height : 720,
// // //         facingMode : "user"
// // //     }
// // // };
// //
// // peerConnection.addStream(stream);
// // peerConnection.onaddstream = function(event) {
// //     videoElement.srcObject = event.stream;
// // };


//WERSJA 2 - PROSTY PROGRAM (nie działa, przeanalizować)
//connecting to our signaling server
var conn = new WebSocket('ws://localhost:8080/socket');

conn.onopen = function() {
    console.log("Connected to the signaling server");
    initialize();
};

conn.onmessage = function(msg) {
    console.log("Got message", msg.data);
    var content = JSON.parse(msg.data);
    var data = content.data;
    switch (content.event) {
        // when somebody wants to call us
        case "offer":
            handleOffer(data);
            break;
        case "answer":
            handleAnswer(data);
            break;
        // when a remote peer sends an ice candidate to us
        case "candidate":
            handleCandidate(data);
            break;
        default:
            break;
    }
};

function send(message) {
    conn.send(JSON.stringify(message));
}

var peerConnection;
var dataChannel;
var input = document.getElementById("messageInput");

function initialize() {
    var configuration = null;

    peerConnection = new RTCPeerConnection(configuration, {
        optional : [ {
            RtpDataChannels : true
        } ]
    });

    // Setup ice handling
    peerConnection.onicecandidate = function(event) {
        if (event.candidate) {
            send({
                event : "candidate",
                data : event.candidate
            });
        }
    };

    // creating data channel
    dataChannel = peerConnection.createDataChannel("dataChannel", {
        reliable : true
    });

    dataChannel.onerror = function(error) {
        console.log("Error occured on datachannel:", error);
    };

    // when we receive a message from the other peer, printing it on the console
    dataChannel.onmessage = function(event) {
        console.log("message:", event.data);
    };

    dataChannel.onclose = function() {
        console.log("data channel is closed");
    };
}

function createOffer() {
    peerConnection.createOffer(function(offer) {
        send({
            event : "offer",
            data : offer
        });
        peerConnection.setLocalDescription(offer);
    }, function(error) {
        alert("Error creating an offer");
    });
}

function handleOffer(offer) {
    peerConnection.setRemoteDescription(new RTCSessionDescription(offer));

    // create and send an answer to an offer
    peerConnection.createAnswer(function(answer) {
        peerConnection.setLocalDescription(answer);
        send({
            event : "answer",
            data : answer
        });
    }, function(error) {
        alert("Error creating an answer");
    });

}

function handleCandidate(candidate) {
    peerConnection.addIceCandidate(new RTCIceCandidate(candidate));
}

function handleAnswer(answer) {
    peerConnection.setRemoteDescription(new RTCSessionDescription(answer));
    console.log("connection established successfully!!");
}

function sendMessage() {
    dataChannel.send(input.value);
    input.value = "";
}