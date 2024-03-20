import { useState } from "react";
import logo from "./logo.png";
import Modal from "../../molecules/Modal";
import HomeIcon from "src/components/atoms/Icons/Home";
import InfoIcon from "src/components/atoms/Icons/Info";
import LogoutIcon from "src/components/atoms/Icons/Logout";

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);

  const openModal = () => {
    setIsOpen(true);
  };

  const closeModal = () => {
    setIsOpen(false);
  };

  const handleLogout = () => {
    localStorage.removeItem("user-token");
  };

  return (
    <div className="m-2">
      <nav className="flex items-center bg-white drop-shadow-lg rounded-lg border border-[#343433]">
        <div className="ml-6 md:my-1 lg:my-2 xl:my-3 2xl:my-4">
          <img
            src={logo}
            alt="fme-logo"
            className="w-14 h-9 my-3 md:w-16 md:h-10 md:my-3 lg:w-20 lg:h-11 lg:my-3 xl:w-22 xl:h-12 xl:my-1"
          />
        </div>

        <div className="flex ml-auto mt-1 mr-3">
          <a
            href="/home"
            className="mr-1"
            data-te-toggle="tooltip"
            title="Home"
          >
            <HomeIcon />
          </a>

          <a className="mr-1" data-te-toggle="tooltip" title="AboutUs">
            <button onClick={openModal}>
              <InfoIcon />
            </button>
          </a>

          <a href="/" className="" data-te-toggle="tooltip" title="Logout">
            <button onClick={handleLogout}>
              <LogoutIcon />
            </button>
          </a>
        </div>
      </nav>

      {isOpen && (
        <div className="fixed inset-0 flex items-center justify-center z-50 backdrop-blur">
          <Modal isOpen={isOpen} closeModal={closeModal} />
        </div>
      )}
    </div>
  );
};

export default Navbar;
