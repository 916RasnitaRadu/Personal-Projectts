import logo from "./logo.png";

interface ModalProps {
  isOpen: boolean;
  closeModal: () => void;
}

const Modal: React.FC<ModalProps> = ({ isOpen, closeModal }) => {
  const modalClasses = isOpen ? "" : "hidden";

  return (
    <div
      id="defaultModal"
      tabIndex={-1}
      aria-hidden={true}
      className={`absolute w-3/4 top-[20%] left-1/2 transform -translate-x-1/2 -translate-y-1/2 border-2 rounded-xl border-[#707070] ${modalClasses}
       md:w-[68%] md:top-[29%] lg:w-[70%] lg:top-[30%] xl:w-1/2 xl:top-[30%] 2xl:w-1/2`}
    >
      <div className="relative">
        <div className="relative bg-white rounded-lg shadow">
          <div className="flex items-start justify-between p-2 border-b rounded-t border-[#343433] md:p-3 lg:p-4 xl:p-5 2xl:p-6">
            <h3 className="text-lg font-semibold text-[#343433] md:text-2xl md:mx-1 lg:text-3xl lg:mx-2 xl:text-3xl xl:mx-3 2xl:text-4xl 2xl:mx-3">
              AboutUs
            </h3>
          </div>

          <div className="flex p-2 space-y-1 md:flex md:p-3 md:space-y-2 lg:flex lg:space-y-4 lg:p-4 xl:flex xl:space-y-5 2xl:flex 2xl:space-y-6">
            <img
              src={logo}
              alt="fme-logo"
              className="w-1/4 h-1/4 mx-3 my-0 md:mx-10 lg:mx-12 xl:mx-14 2xl:mx-16"
            />
            <div className="mx-7 text-xs font-medium leading-relaxed text-[#343433] md:text-base md:mx-10 lg:text-lg xl:text-xl 2xl:text-2xl">
              <p>Developed by: fme SRL</p>
              <p>Version: 1.0</p>
            </div>
          </div>

          <div className="flex justify-end p-2 space-x-2 border-t border-[#343433] rounded-b md:p-4 lg:p-5 xl:p-6 2xl:p-6">
            <button
              onClick={closeModal}
              data-modal-hide="defaultModal"
              type="button"
              className="text-[#343433] hover:bg-[#343433] focus:ring-4 focus:outline-none rounded-md border border-[#343433] text-sm font-medium px-3 py-0.5 hover:text-white
              md:text-base md:px-5 lg:text-lg lg:px-6 xl:text-xl xl:px-6 2xl:text-2xl 2xl:px-6"
            >
              Close
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Modal;
