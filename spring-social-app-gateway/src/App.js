import React, {useState, useEffect} from 'react';
import './App.css';
import {w3cwebsocket as W3CWebSocket} from "websocket";
import ChatBox from './ChatBox.js'
import ImageView from './ImageView.js'



function App() {


    const emptyString = ""
    const [userName, setUsername] = useState(emptyString)
    const [isLoggedIn, setLoggedIn] = useState(false)
    const [chatText, setChatText] = useState(emptyString)
    const [inboudConnection,setInBoudConnection]=useState(undefined)
    const [outboudConnection,setOutBoudConnection]=useState(undefined)

    function initChatServiceOutBoundConnection(userName) {
        const outboundChatMessages = new W3CWebSocket(`ws://localhost:8300/app/chatMessage.new?user=${userName}`);
        outboundChatMessages.onopen = () => {
            console.log("outbound Chat Client Connected")
        }
        console.log("opening socket connection out bound")
        setOutBoudConnection(outboundChatMessages);
    }

    function initChatServiceInBoundConnection(userName) {
        const inboundChatMessages = new W3CWebSocket(`ws://localhost:8300/topic/chatMessage.new?user=${userName}`);

        inboundChatMessages.onopen = () => {
            console.log("inBound Chat Client Connected")
        }
        inboundChatMessages.onmessage = function (event) {
            console.log('Received ' + event.data);
            setChatText((prevText) => {
                setChatText(prevText + event.data + "\n")
            })
        };
        console.log("opening socket connection in bound")
        setInBoudConnection(inboundChatMessages);
    }



    function logIn() {
        console.log("logging with username " + userName)
        initChatServiceInBoundConnection(userName)
        initChatServiceOutBoundConnection(userName);
        setLoggedIn(true);
    }

    function updateUsername(event) {

        const {name, value} = event.target;
        console.log(name + " is modified to " + value)
        setUsername(value)
    }

    function logOut() {
        setUsername(emptyString)
        setChatText(emptyString)
        inboudConnection.close();
        outboudConnection.close();
        setLoggedIn(false);
    }

    function sendChatMessage(message) {
        if (outboudConnection != null) {
            console.log("sending chat message "+message)
            outboudConnection.send(message);
        }
    }

    const logInStyle = {display: isLoggedIn ? "none" : "inline"}
    const loggedInStyle = {display: isLoggedIn ? "inline" : "none"}
    const loggedInComponenets = isLoggedIn && (
            <div>
                <ImageView/>
                <br/><br/>
                <ChatBox loggedInStyle={loggedInStyle} chatText={chatText} sendChatMessage={sendChatMessage}/>
            </div>)
    return (
        <div>
            <h1>Cloud Cafe</h1>
            <div>
                <h3>Welcome {userName}!</h3>
                <input id="username" type="text" name="username" value={userName} onChange={updateUsername}
                       style={logInStyle}/>
                <button id="connect" onClick={logIn} style={logInStyle}>
                    Connect
                </button>
                <button id="disconnect" onClick={logOut} style={loggedInStyle}>Disconnect</button>
                {loggedInComponenets}
            </div>

        </div>
    );
}

export default App;
