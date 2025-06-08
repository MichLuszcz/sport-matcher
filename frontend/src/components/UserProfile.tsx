import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./UserProfile.css";

type Ad = {
  id: number;
  title: string;
  eventDateTime: string;
  location: string;
};

type User = {
  id: number;
  username: string;
  email: string;
  name: string;
  dateCreated: string;
  isActive: boolean;
  role: string;
  ads: Ad[];
};

export default function UserProfile() {
  const navigate = useNavigate();

  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    // backend
  }, []);

  const handleLogout = () => {
    // backend wylogowanie
    navigate("/");
  };

  if (!user) return <div className="user-profile-container">Loading...</div>;

  return (
    <div className="user-profile-container">
      <h2>My Profile</h2>

      <div className="user-profile-card">
        <div className="user-profile-field">
          <span className="user-profile-label">Username:</span>
          <span className="user-profile-value">{user.username}</span>
        </div>
        <div className="user-profile-field">
          <span className="user-profile-label">Name:</span>
          <span className="user-profile-value">{user.name}</span>
        </div>
        <div className="user-profile-field">
          <span className="user-profile-label">Email:</span>
          <span className="user-profile-value">{user.email}</span>
        </div>
        <div className="user-profile-field">
          <span className="user-profile-label">Account created:</span>
          <span className="user-profile-value">
            {new Date(user.dateCreated).toLocaleString()}
          </span>
        </div>
        <div className="user-profile-field">
          <span className="user-profile-label">Status:</span>
          <span className="user-profile-value">
            {user.isActive ? "Active" : "Inactive"}
          </span>
        </div>
      </div>

      <div className="user-profile-buttons">
        <button onClick={() => alert("dokończyć edytowanie profilu")} className="user-button">Edit Profile </button>
        <button onClick={() => alert("dokończyć zmiana hasla")} className="user-button">Change Password</button>
        <button onClick={() => alert("dokończyć ogłoszenia")} className="user-button">My Ads</button>
        <button onClick={handleLogout} className="user-button logout">Log Out</button>
      </div>
    </div>
    // zapomniałam zrobić przycisku do wyświetlania i ew dodania nowego ogłoszenia/przejścia do wszystkich ogłoszeń, jeszcze css trzeba zrobić lepszy
  );
}
