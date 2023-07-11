import React from "react"
import { createRoot } from "react-dom/client"
import Main from "./Main"
import client from "./client"

const container = document.getElementById("react")
const root = createRoot(container)
root.render(<Main />)