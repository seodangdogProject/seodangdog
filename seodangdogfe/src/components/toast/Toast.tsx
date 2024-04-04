import React, { useEffect, useState, useRef } from "react";
import styles from "./toast.module.css";

export default function ToastPopup({
    message,
    setToast,
}: {
    message: string;
    setToast: React.Dispatch<React.SetStateAction<boolean>>;
}) {
    useEffect(() => {
        const timer = setTimeout(() => {
            setToast(false);
        }, 1500);
        return () => {
            clearTimeout(timer);
        };
    }, [setToast]);

    return (
        <div className={styles.toastbox}>
            <p className="text-Body text-white">{message}</p>
        </div>
    );
}
