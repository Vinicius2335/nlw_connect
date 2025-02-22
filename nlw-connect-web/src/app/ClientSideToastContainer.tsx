"use client"

import "react-toastify/dist/ReactToastify.css"
import { ToastContainer, Zoom } from "react-toastify"

const ClientSideToastContainer = () => (
  <ToastContainer
    position="top-center"
    autoClose={5000}
    limit={1}
    hideProgressBar={false}
    newestOnTop={false}
    closeOnClick
    rtl={false}
    pauseOnFocusLoss
    draggable
    pauseOnHover={false}
    theme="dark"
    transition={Zoom}
  />
)

export default ClientSideToastContainer
