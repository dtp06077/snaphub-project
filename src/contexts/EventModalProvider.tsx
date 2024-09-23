import React, { createContext, useState, ReactNode } from "react";
import EventModal from "../modals/EventModal"

// 모든 컴포넌트에서 활용 가능한 event modal
export const EventModalContext = createContext<{
  showModal: (title: string, message: string) => void;
} | undefined>(undefined);

interface EventModalContextProviderProps {
  children: ReactNode;
}

const EventModalContextProvider: React.FC<EventModalContextProviderProps> = ({ children }) => {
    const [eventModalOn, setEventModalOn] = useState(false);
    const [modalContent, setModalContent] = useState<{ title: string; message: string }>({ title: '', message: '' });

    const showModal = (title: string, message: string) => {
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