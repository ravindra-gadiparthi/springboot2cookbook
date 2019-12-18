import React,{useState} from 'react'



function ChatBox(props)
{

    const [message, setMessage] = useState("")

    function updateMessage(event) {
        const {value} = event.target;
        setMessage(value)
    }


    return (

        <div id="chatBox" style={props.loggedInStyle}>
            Greetings!
            <br/>
            <textarea id="chatDisplay" rows="10" cols="80"
                      disabled={true} value={props.chatText}></textarea>
            <br/>
            <input id="chatInput" type="text" name="message" style={{"width": "500px"}}
                   value={props.message} onChange={updateMessage}/>
            <br/>
            <button id="chatButton" onClick={()=>props.sendChatMessage(message)}>Send</button>
            <br/>
        </div>
    )
}

export default ChatBox