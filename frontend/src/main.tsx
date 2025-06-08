import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
// import Header from "./components/Header.tsx";
import Login from "./components/Login.tsx";
import Signup from "./components/Signup.tsx";
import AdList from "./components/AdList.tsx";
import AdForm from "./components/AdForm.tsx";
import UserProfile from "./components/UserProfile.tsx";
import AdPage from "./components/AdPage.tsx";
import RequestSent from "./components/RequestSent.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
  {
    path: "/login", 
    element: <Login />
  }, 
  {
    path: "/signup", 
    element: <Signup />
  },
  {
    path: "//ads/:id", 
    element: <AdPage />
  },
  {
    path: "/ads", 
    element: <AdList />
  },
  {
    path: "/ads/new", 
    element: <AdForm />
  },
  {
    path: "/profile", 
    element: <UserProfile />
  },
  {
  path: "/ads/:id/joined",
  element: <RequestSent />,
}

]);

const root = createRoot(document.getElementById("root")!);
root.render(
  <StrictMode>
    {/* <Header /> */}
    <RouterProvider router={router} />
  </StrictMode>
);
