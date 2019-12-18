import React, {useState, useEffect} from 'react'
import {w3cwebsocket as W3CWebSocket} from "websocket";

function ImageView(props) {

    const [images, setImages] = useState([])
    let commentClient = new W3CWebSocket("ws://localhost:8300/topic/comments.new")


    function initCommentServiceSocketConnection() {
        commentClient.onopen = () => {
            console.log("chat socket connection")
        }
        commentClient.onmessage = (event) => {
            console.log(`websocket message has received ${event.data}`)
            const newComment = JSON.parse(event.data);

            setImages((prevImages) => {
                const newImages = [...prevImages];
                newImages.map(image => {
                    if(image.id===newComment.imageId) {
                        image.comments.push(newComment)
                    }
                });
                return newImages;
            })
        }
    }

    function loadImages() {
        fetch(`http://localhost:8080/`, {
            headers: {
                'Accept': 'application/json',
                'Access-Control-Allow-Origin': '*',
            }
        })
            .then(resp => resp.json())
            .then((data) => {
                setImages(data)
            })
    }

    function deleteImage(imageName) {
        console.log("delete image name " + imageName)
        fetch(`http://localhost:8080/images/${imageName}`, {
            method: "delete",
            headers: {
                'Accept': 'application/json',
                'Access-Control-Allow-Origin': '*',
            }
        }).then(() => loadImages())
    }

    function saveComment(event) {

        event.preventDefault()

        const data = new FormData(event.target);
        const comment = JSON.stringify({imageId: data.get("imageId"), comment: data.get("comment")})
        console.log(`save Image Id ${comment}`)

        fetch(`http://localhost:8000/api/comments`, {
            method: "post",
            body: comment,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).catch((err) => console.log(err));
    }

    function saveImage(event) {
        console.log("save Image ")
        event.preventDefault()

        const form = event.target;
        const data = new FormData(form);

        fetch("http://localhost:8080/images", {
            method: "post",
            body: data,
            headers: {
                'Accept': 'multipart/form-data',
                'Access-Control-Allow-Origin': '*',
            }
        }).then(() => loadImages())
            .catch((err) => console.log(err));
    }

    useEffect(() => {
        loadImages()
        initCommentServiceSocketConnection()
    }, [])

    const imageComponents = images.length > 0 && images.map(image =>
        <tr key={image.id}>
            <td>{image.id}</td>
            <td>{image.name}</td>
            <td><img src={`http://localhost:8080/images/${image.name}/raw`} alt={image.name} className="thumbnail"/>
            </td>
            <td>
                <input type="submit" name="submit" onClick={() => deleteImage(image.name)} value="Delete"/>
            </td>
            <td>
                <ul id={`comment-${image.id}`}>
                    {image.comments.map(comment => <li key={comment.id}> {comment.comment}</li>)}
                </ul>
            </td>
            <td>
                <form onSubmit={saveComment}>
                    <p><input type="text" name="comment"/></p>
                    <p><input type="text" name="imageId" value={image.id} hidden={true} onChange={() => {
                    }}/></p>
                    <p><input type="submit" value="Add Comment"/></p>
                </form>

            </td>
        </tr>);

    return (
        <div>
         <table>
                <thead>
                <tr>
                    <th>Image Id</th>
                    <th>Image Name</th>
                    <th>Image Url</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                  {imageComponents}
                </tbody>
       </table>
            <form onSubmit={saveImage}>
                <p><input type="file" name="file"/></p>
                <p><input type="submit" value="Upload"/></p>
            </form>
            </div>);
}

export default ImageView