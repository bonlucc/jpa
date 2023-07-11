import React from "react"

export default function Main() {

    //////////////////////////////////////////////////////////////
    const [state, setState] = React.useState("")
    function getCourses() {
        //let response
        fetch("http://localhost:8080/api/school/getCourses")
            .then(rp => rp.json())
            .then(res => setState(JSON.stringify(res)))

    }
    //////////////////////////////////////////////////////////////

    const [formData, setFormData] = React.useState(
        {
            title: '',
            credit: 0
        }
    )


    function createCourse(event) {
        event.preventDefault()
        fetch("http://localhost:8080/api/school/createCourse", {
            method: "POST",
            body: JSON.stringify(formData),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        })
            .then(resp => resp.json())
            .then(res => console.log(res))
        window.alert("successful")
    }
    function handleChange(event){
        const {name, value} = event.target
        setFormData(prevState => {
            return {...prevState,
                    [name]: value
            }
        })
    }

    //const stateElements = state.map(elem => <h1>{elem}</h1>)
    return(
        <main style={{backgroundColor: "black"}}>
            <h1 style={{color: "white"}}>Hello World</h1>
            <button onClick={getCourses}>Click to get the Courses</button>
            <form onSubmit={createCourse}>
                <input
                type="text"
                placeholder="Title"
                onChange={handleChange}
                name="title"
                value={formData.title}
                />
                <input
                    type="number"
                    placeholder="Credit"
                    onChange={handleChange}
                    name="credit"
                    value={formData.credit}
                />
                <button>Push Course</button>
            </form>
            <h2 style={{color: "blue"}}>{state}</h2>
        </main>
    )
}