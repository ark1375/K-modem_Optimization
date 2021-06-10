#### PLY TO WKT CONVERTOR FOR NORMAL - UNHOLED POLYGONS   

import os.path as path 
import sys
import argparse
import regex as  reg
from os import walk
import re


def main():

    func_dict_single = {'ply_to_wkt' : ply_to_wkt}
    func_dict_mult = {'ply_to_wkt' : ply_to_wkt_multiple} 
    ### Parser Part
    parser = argparse.ArgumentParser(description = 'Polygon convertor v0.01')
    parser.add_argument('func', help ='Type of conversion.', choices = ['ply_to_wkt'], metavar = '[function]')
    parser.add_argument('sourcePath' , help = "Input file-s address." , metavar = '[input]' )
    parser.add_argument('-a' ,'-all' , help = 'Convert all the files in the directory.' , action = 'store_true' , dest = 'mos' , default = False)
    parser.add_argument('-d','--dest' , help ="Destination directory.")
    parser.add_argument('-o' , '--out' , help = 'Output File\'s name - For nultiple files, this will be used as files prefix.' , dest = 'destFileName' , default='' )
    parser.add_argument('-m', '--multiplier', help = "The Multiplier." , dest = 'multiplier' , type = int , default = 1)
    args = parser.parse_args()
    ### End Parser Part

    ### Parameter Validation
    if (not args.mos and not path.isfile(args.sourcePath)):
        parser.error('Invalid input address')

    if (args.mos and not path.isdir(args.sourcePath)):
        parser.error('Invalid input address')

    if (args.dest and not path.isdir(args.dest)):
        parser.error('Invalid destination path.')
    ### End Validation


    if (args.mos):
        if (args.func in func_dict_mult.keys()):
            func_dict_mult[args.func]( args.sourcePath , args.dest , args.destFileName , args.multiplier)
        else:   
            parser.error('Wrong function name entered!')
    
    else:
        if (args.func in func_dict_single.keys()):
            func_dict_single[args.func]( args.sourcePath , args.dest , args.destFileName , args.multiplier)
        else:   
            parser.error('Wrong function name entered!')


    for param in vars(args):
        print(param)



def ply_to_wkt(sourceAdd, destAdd = '' , destName = '', multiplier = 1 ):
    
    if (not destAdd):
        destAdd = path.split(sourceAdd)[0]
    
    if (not destName):
        destName = path.split(sourceAdd)[1].rsplit('.')[0]

    file = open(sourceAdd , 'r')

    text = file.read()
    text = text.split('\n')
    text = text[ text.index('end_header') + 1 : -1]
    wkt_data = map( lambda x : str( float(x.split(' ')[0]) * multiplier ) + ' ' + str( float(x.split(' ')[1]) * multiplier ) , text )
    wkt_data = list(wkt_data)[:-1]
    wkt_data.append(wkt_data[0])
    wkt_data = ', '.join(wkt_data)
    wkt_data = 'POLYGON((' + wkt_data + '))'

    file.close()
    file = open(path.join(destAdd , destName + '.wkt') , 'w')
    file.write(wkt_data)

    file.close()

def ply_to_wkt_multiple(sourceAdd, destAdd = '' , destName = '', multiplier = 1 ):
    
    if (not destAdd):
        destAdd = sourceAdd

    dir, _, files = next(walk(sourceAdd))
    for name in files:
        if (path.splitext(name)[1] == '.ply'):
            ply_to_wkt(path.join(dir , name) , destAdd , destName + path.splitext(name)[0] , multiplier = multiplier)

def wktMuliplier(sourceAdd , destAdd , mult = 1):
    filenames = next(walk(sourceAdd))[2]

    for name in filenames:
        file = open(path.join(sourceAdd , name) ,'r')
        file = file.read()
        file = file[9:-1].split('\n')

        for i , line in enumerate(file):

            numbers = re.findall('([0-9]+.[0-9]+)' , line)
            numbers = list(map(float , numbers))
            numbers = list(map(lambda x: int(x * mult) , numbers))
            numbers = list(map(lambda x , y : str(x) + ' ' + str(y) , numbers[::2] , numbers[1::2]))
            file[i] = '(' + ', '.join(numbers) + ')'
        file = 'POLYGON(' + ', '.join(file) + ')'
        
        out = open(path.join(destAdd , name) , 'w')
        out.write(file)
        out.close()


# def convert(sourcePath , destPath):
#     file = open(path1 , mode = 'r')
#     file = file.readlines()[10:-1]
#     file = [a[:-3] for a in file]
#     file.append(file[0])
#     out = ', '.join(file)
#     out = 'POLYGON((' + out + '))'
#     path = path1[:path1.rfind('.')]+'.wkt'
#     file = open(path , 'w')
#     file.write(out)
#     file.close()
    

if __name__ == '__main__':
    main()
