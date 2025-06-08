import "./Header.css";
import { useNavigate } from "react-router-dom";

// const logo = undefined;

function Header() {
  const navigate = useNavigate();

  return (
    <header>
      {/* zakomentowałam to co było, jakbym coś popsuła */}
      {/* <img src={logo} className="logo" />
      <button>Find sports</button>
      <button className="sign-in-button">Log in</button>
      <button className="sign-up-button">Sign up</button> */}

      <div className="logo" onClick={() => navigate("/")}></div>
      <button onClick={() => navigate("/ads")}>Find sports</button>
      <button onClick={() => navigate("/login")}>Sign in</button>
      <button onClick={() => navigate("/signup")}>Sign up</button>
   
    </header>
  );
}

export default Header;
