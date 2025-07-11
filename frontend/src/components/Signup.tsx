import { useState } from "react";
import { useNavigate } from "react-router-dom";
import ImageSlideshow from "../ImageSlideshow";

export default function Signup() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const [message, setMessage] = useState("");
  const [name, setName] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await fetch(`${import.meta.env.VITE_API_URL}/api/users`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, name, email, password }),
      });

      if (response.ok) {
        setMessage("Registration successful!");
        setTimeout(() => navigate("/login"), 1500);
      } else {
        const errorText = await response.text();
        setMessage("Registration failed: " + errorText);
      }
    } catch (error) {
      setMessage("Unable to connect to the server.");
      if (error instanceof Error) {
        console.error("An error occured while signing up:\n" + error);
      }
    }
  };

  return (
    <div className="fullscreen">
      <ImageSlideshow />
      <div className="auth-box">
        <h2>Sign up to Sports Matcher</h2>
        <form onSubmit={handleSubmit}>
          <input type="text" placeholder="Name" value={name} onChange={(event) => setName(event.target.value)} />
          <input type="text" placeholder="Username" value={username} onChange={(event) => setUsername(event.target.value)} />
          <input type="email" placeholder="Email" value={email} onChange={(event) => setEmail(event.target.value)} />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
          />
          <button type="submit">Sign up</button>
        </form>
        {message && <p>{message}</p>}
      </div>
    </div>
  );
}
