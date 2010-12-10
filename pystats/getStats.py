import os


class statObject:
	valid = True
	
	def __init__(self, p):
		self.bytes = 0
		self.publicCount = 0
		self.privateCount = 0
		self.tryCount = 0
		self.catchCount = 0
		self.children = []
		self.path = p
		self.isDir = os.path.isdir(self.path)
		
		if self.isDir:
			contents = os.listdir(self.path)
			for p in contents:
				child = statObject(self.path +"/"+ p)
				if child.valid:
					self.children.append(child)
					self.bytes += child.bytes
					self.publicCount += child.publicCount
					self.privateCount += child.privateCount
					self.tryCount += child.tryCount
					self.catchCount += child.catchCount
		else:
			if os.path.splitext(self.path)[1] != '.java':
				self.valid = False
			else:
				self.bytes = os.path.getsize(self.path)
				f = open(self.path, 'r')
				inComment = False
				ln = 0
				for line in f:
					ln += 1
					if inComment:
						cterm = line.find("*/")
						if cterm == -1:
							continue
						else:
							inComment = False
							if line.find("public ", cterm) != -1:
								self.publicCount += 1
							if line.find("private ", cterm) != -1:
								self.privateCount += 1
							if line.find("try", cterm) != -1:
								self.tryCount += 1
							if line.find("catch", cterm) != -1:
								self.catchCount += 1
							continue
					else:
						cterm = line.find("//")
						if cterm == -1:
							cterm = line.find("/*")
							if cterm != -1:
								inComment = True
						if cterm != -1:
							if line.find("public ", 0, cterm) != -1:
								self.publicCount += 1
							if line.find("private ", 0, cterm) != -1:
								self.privateCount += 1
							if line.find("try", 0, cterm) != -1:
								self.tryCount += 1
							if line.find("catch", 0, cterm) != -1:
								self.catchCount += 1
						else:
							if line.find("public ") != -1:
								self.publicCount += 1
							if line.find("private ") != -1:
								self.privateCount += 1
							if line.find("try") != -1:
								self.tryCount += 1
							if line.find("catch") != -1:
								self.catchCount += 1
	
	def Print(self):
		if not self.isDir:
			return
		print self.path
		print "\t"+str(self.bytes)+" bytes\t"+str(self.publicCount)+" public\t"+str(self.privateCount)+" private\t"+str(self.tryCount)+" try\t"+str(self.catchCount)+" catch"
		for c in self.children:
			c.Print()
			
def getStats(path):
	root = statObject(path)
	if root == None:
		print "Invalid Root"
	else:
		root.Print()
def main():
	getStats("testcases/part1/testcase2/root_dir")

if __name__ == "__main__":
    main()

