import React, { createContext, useState, ReactNode } from "react";
import ClickEventModal from "../modals/ClickEventModal";

// 모든 컴포넌트에서 활용 가능한 ClickEventModal
export const ClickEventModalContext = createContext<{
  showClickModal: (title: string, message: string, onConfirm: () => void, onCancel: () => void) => void;
} | undefined>(undefined);

interface ClickEventModalContextProviderProps {
  children: ReactNode;
}

const ClickEventModalContextProvider: React.FC<ClickEventModalContextProviderProps> = ({ children }) => {
  const [modalVisible, setModalVisible] = useState(false);
  const [modalContent, setModalContent] = useState<{ title: string; message: string; onConfirm: () => void; onCancel: () => void; }>({ 
    title: '', 
    message: '', 
    onConfirm: () => {}, 
    onCancel: () => {} 
  });

  const showClickModal = (title: string, message: string, onConfirm: () => void, onCancel: () => void) => {
    setModalContent({ title, message, onConfirm, onCancel });
    setModalVisible(true);
  };

  const hideModal = () => {
    setModalVisible(false);
  };

  return (
    <ClickEventModalContext.Provider value={{ showClickModal }}>
      {children}
      <ClickEventModal
        show={modalVisible}
        onHide={hideModal}
        title={modalContent.title}
        message={modalContent.message}
        onConfirm={() => {
          modalContent.onConfirm(); // 예 버튼 클릭 시 호출
          hideModal(); // 모달 숨김
        }}
        onCancel={() => {
          modalContent.onCancel(); // 아니오 버튼 클릭 시 호출
          hideModal(); // 모달 숨김
        }}
      />
    </ClickEventModalContext.Provider>
  );
}

export default ClickEventModalContextProvider;