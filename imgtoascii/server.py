#Tasnadi Ede, 524/2

from socket import *
import threading
import os.path
import re
from PIL import Image
import numpy as np

def imgtoascii(path):

    scale = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/()1{}[]?-_+~<>i!lI;:,^`'."
    output = ""

    image = Image.open(path)
    image = np.asarray(image)
    for line in image:
        for pixel in line:
            v = (float(pixel[0])* 0.299 + float(pixel[1]) * 0.587 + float(pixel[2]) * 0.114) / 255.0 * len(scale)
            output += scale[int(v) - 1] + " "
            
        output += "\n"
    
    return output

def postRequest(bytes, connectionSocket):
    file = b""
    
    div = bytes[(bytes.index(b"boundary=") + 9):]
    div = div[:div.index(b"\r\n")]
    print("===============================================================================================",div)
    temp = bytes[(bytes.index(div) + len(div)):]
    
    try:
        if re.search(div, temp):
            temp2 = temp[(temp.index(div) + len(div)):]
            if re.search(b"\r\n\r\n", temp2):
                file += temp2[(temp2.index(b"\r\n\r\n") + 4):]
            
        if not(re.search(div, file)):
            
            bytes = connectionSocket.recv(65535)
            
            if re.search(div, bytes) and len(file) == 0:
                bytes = bytes[(bytes.index(div) + len(div)):]
                bytes = bytes[(bytes.index(b"\r\n\r\n") + 4):]
            
            while not(re.search(div, file)) and len(bytes) != 0:
                file += bytes
                bytes = connectionSocket.recv(65535)
                
            if len(bytes) != 0:
                file += bytes[:bytes.index(div)]
        
        if re.search(div, file):
            file = file[:file.index(div)]

    except Exception as e:
        print("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + str(e))    
    
    if re.search(div, file):
        file = file[:file.index(div)]
        
    print("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
    f = open('C:\\Users\\tasna\\Desktop\\pain II.2\\Projects\\imgtoascii\\magic.png', 'wb')
    f.write(file)
    f.close()
    
    ansHTML()
    
def ansHTML():
    file = """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>The doer</title>
</head>
<body>
    <a href="index.html">Return to main page</a>
    <tt><p>
    """ + imgtoascii("C:\\Users\\tasna\\Desktop\\pain II.2\\Projects\\imgtoascii\\magic.png").replace("\n", "<br>") +  """
    </p></tt>
</body>
</html>
    """
    f = open('C:\\Users\\tasna\\Desktop\\pain II.2\\Projects\\imgtoascii\\answer.html', 'w')
    f.write(file)
    f.close()
    
    
    
def getRequest(incomingMessage, connectionSocket):
    if len(incomingMessage) == 0:
        print("[" + str(id) + "]: " + str(threading.current_thread().ident) + " shut down")
        return "break"
    
    file = incomingMessage.split(" ")[1][1:]
    print("[" + str(id) + "]: " + file + " requested")
    
    finalRequest = False
    conncectionType = "keep-alive"
    for line in incomingMessage.split("\n"):
        if re.search("^Connection:", line):
            conncectionType = line.split(" ")[1]
            if conncectionType == "close":
                finalRequest = True
            break

    filePath = "C:\\Users\\tasna\\Desktop\\pain II.2\\Projects\\imgtoascii\\" + file
    
    if os.path.isfile(filePath):
        returnMessageContent = open(filePath, "rb").read()
        returnMessageHeader = "HTTP/1.1 200 OK\nContent-Type: " + fileTypes[file.split(".")[1]]+ "\nConnection: " + conncectionType + "\nContent-Length: " + str(len(returnMessageContent)) + "\n\n"
        returnMessage = returnMessageHeader.encode() + returnMessageContent
        print("[" + str(id) + "]: " + file + " found")
    else:
        returnMessage = "HTTP/1.1 404 File not found\nContent-Type: " + fileTypes[file.split(".")[1]] + "\nConnection: " + conncectionType + "\nContent-Length: 0\n\n"
        returnMessage = returnMessage.encode()
        print("[" + str(id) + "]: " + file + " not found")
    
    connectionSocket.send(returnMessage)
    
    if finalRequest:
        print("[" + str(id) + "]: " + str(threading.current_thread().ident) + " shut down")
        return "break"
    
def clientThread(connectionSocket, id):
    connectionSocket.settimeout(2)
    
    try:
        while True:
            incomingMessageCoded = connectionSocket.recv(65535)
            incomingMessage = incomingMessageCoded.decode(errors='ignore')
            print("----------------------------------------------------")
            print(incomingMessage)
            print("----------------------------------------------------")
            
            if re.search(r"^GET", incomingMessage):
                if getRequest(incomingMessage, connectionSocket) == "break":
                    break
            else:
                postRequest(incomingMessageCoded, connectionSocket)
                response = "HTTP/1.1 303 See Other\r\nLocation: /answer.html\r\n\r\n"
                connectionSocket.send(response.encode())
            
    except Exception as e:
        print("[" + str(id) + "]: " + str(e))
        # connectionSocket.close()
        # print("[" + str(id) + "]: " + str(threading.current_thread().ident) + " timed out")

    
    
serverPort = 12009
serverSocket = socket(AF_INET, SOCK_STREAM)
serverSocket.bind(('', serverPort))
serverSocket.listen(1)
fileTypes = {'html': 'text/html','css': 'text/css','jpg': 'image/jpeg', 'png': 'image/png', 'mp4': 'video/mp4', 'ico': 'image/png', }
id = 0
ids = []

while True:
    
    connectionSocket, clientAddress = serverSocket.accept()
    
    #clientThread(connectionSocket, id + 1)
    thread = threading.Thread(target=clientThread, args=(connectionSocket, id + 1))
    
    print("[" + str(id + 1) + "]: started")
    thread.start()
    
    id += 1
