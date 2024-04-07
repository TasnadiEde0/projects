from PIL import Image
import numpy as np
import argparse
import sys

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


print(imgtoascii("C:\\Users\\tasna\\Desktop\\pain II.2\\Projects\\magic.png"))