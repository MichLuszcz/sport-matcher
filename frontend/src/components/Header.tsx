import "./Header.css";

const logo = undefined;

function Header() {
  return (
    <header>
      <img src={logo} className="logo" />
      <button>Find sports</button>
      <button className="log-in-button">Log in</button>
      <button className="sign-up-button">Sign up</button>
    </header>
  );
}

export default Header;
