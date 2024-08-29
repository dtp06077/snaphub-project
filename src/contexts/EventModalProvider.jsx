import { createContext, useState } from "react";
import EventModal from "../modals/EventModal";

// 모든 컴포넌트에서 활용 가능한 event modal
export const EventModalContext = createContext();

const EventModalContextProvider = ({ children }) => {
    const [eventModalOn, setEventModalOn] = useState(false);
    const [modalContent, setModalContent] = useState({ title: '', message: '' });

    const showModal = (title, message) => {
        setModalContent({ title, message });
        setEventModalOn(true);
    };

    const hideModal = () => {
        setEventModalOn(false);
    };

    return (
        <EventModalContext.Provider value={{ showModal }}>
            {children}
            <EventModal
                show={eventModalOn}
                onHide={hideModal}
                title={modalContent.title}
                message={modalContent.message}
            />
        </EventModalContext.Provider>
    );
}

export default EventModalContextProvider;