import Cards from "../../atoms/Cards";
import Navbar from "../../organisms/Header/Navbar";

const HomePage = () => {
  return (
    <div className="min-h-screen bg-white ">
      <Navbar />
      <div className="overflow-y-scroll h-[90vh] flex flex-wrap">
        <Cards retrieveBucketState={true} />
      </div>
    </div>
  );
};

export default HomePage;
