import { useState } from "react";
import { useNavigate } from "react-router-dom";
import ImageSlideshow from "../ImageSlideshow";
import './SignForm.css';

export default function Login() {
  const [usernameOrEmail, setUsernameOrEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    //backend
    navigate("/ads");
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
          <button type="submit" className="header-button">Sign in</button>
        </form>
      </div>
    </div>
  );
}
