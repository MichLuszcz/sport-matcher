import { useState } from "react";
import { useNavigate } from "react-router-dom";
import ImageSlideshow from "../ImageSlideshow";
import "./SignForm.css";

export default function Login() {
  const [usernameOrEmail, setUsernameOrEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const [message, setMessage] = useState("");

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    console.log("Form submitted with:", usernameOrEmail, password); // DEBUG

    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/auth/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: usernameOrEmail,
          password: password,
        }),
      });

      if (response.ok) {
        // Give this a TypeScript type for type safety
        const data = await response.json();
        const token = data.accessToken;

        localStorage.setItem("accessToken", token);
        navigate("/ads");
      } else {
        const errorText = await response.text();
        setMessage("Login failed: " + errorText);
      }
    } catch (error) {
      setMessage("Unable to connect to the server.");
      if (error instanceof Error) {
        console.error("An error occured while logging in:\n" + error);
      }
    }
  };

  return (
    <div className="fullscreen">
      <ImageSlideshow />
      <div className="auth-box">
        <h2>Sign in to Sports Matcher</h2>
        <form onSubmit={handleSubmit} className="textbox">
          <input
            type="text"
            placeholder="Username or Email"
            value={usernameOrEmail}
            onChange={(e) => setUsernameOrEmail(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button type="submit" className="header-button">
            Sign in
          </button>
        </form>
        {(() => {
          if (message) return <p style={{ color: "red", marginTop: "10px" }}>{message}</p>;
        })()}
      </div>
    </div>
  );
}
