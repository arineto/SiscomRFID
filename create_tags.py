import os
import random

def generate_id():
    tag_id = "{0:b}".format(random.randint(0, 2**96 - 1))
    return tag_id


def main():
    tagsNum = range(100,1001,100)

    for num in tagsNum:

        directory = '%d' % num
        if not os.path.exists(directory):
            os.makedirs(directory)

        for i in range(1, num+1):

            file_name = '%s/%d' % (directory, i)
            f = open(file_name, 'w')
            tag_id = generate_id()
            f.write(tag_id)
            f.close()


if __name__ == "__main__":
    main()
