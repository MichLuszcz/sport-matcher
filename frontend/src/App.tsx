import "./App.css";
import ImageSlideshow from "./ImageSlideshow";

function App() {
  return (
    <main>
      <div className="stack fit">
        <ImageSlideshow />
        <div className="textbox top">
          <h1>Sports Matcher</h1>
          <span>Bored? Find people to play sports games with!</span>
          <div className="flex-horizontal">
            <button>Find sports</button>
          </div>
        </div>
      </div>
    </main>
  );
}

export default App;
