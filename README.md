# Instagram Clone

This Android app is a basic implementation of an Instagram-like platform, allowing users to upload photos and reels, follow others, and manage their profiles. It provides a simple yet functional social media experience with a clean and responsive UI.
---

## Features
1. **Dual Camera Support**: Easily switch between the front and back cameras for object detection.
2. **Real-Time Object Detection**: Utilizes the YOLO model to detect multiple objects in real-time with high accuracy.
3. **Bounding Boxes and Labels**: Detected objects are highlighted with bounding boxes and class labels for better visualization.
4. **Efficient Processing**: Powered by TensorFlow Lite for smooth and efficient model inference on Android devices.
5. **User-Friendly Interface**: Designed with Jetpack Compose for a clean, responsive, and intuitive UI.
---

## Technical Implementation

### YOLO Model Integration
- The app integrates the YOLOv5 model converted to TensorFlow Lite for fast, on-device inference.
- Models are optimized for mobile devices to balance accuracy and performance.

### Object Visualization
- Detected objects are overlaid with dynamic bounding boxes and labels.
- The UI updates instantly with every frame, ensuring smooth object tracking.
### Performance Optimization
- Efficient use of background threads for model processing to keep the UI responsive.
- Uses TensorFlow Lite’s GPU delegate for accelerated performance (if available).

---

## User Experience
1. Launch the App → Grant Camera Permission → Real-time Object Detection.
2. Use the Switch Camera button to toggle between front and back cameras.
3. View detected objects with live bounding boxes and labels.


<img src="https://github.com/Alenaak/Detectify/blob/main/images/SCREENSHOT.png" alt="Application Interface" width="600"/>


---

## Error Handling
- **Camera Access Denied**: Prompts the user to grant camera permissions if denied.

---

## Conclusion
The Detectify App is a powerful and efficient app for real-time object detection using both the front and back cameras. By combining the YOLO model with TensorFlow Lite and Camera, the app offers accurate detection with a user-friendly interface, making it perfect for everyday use and practical applications in object recognition.
