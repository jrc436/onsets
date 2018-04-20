import sys
import numpy as np

f = open(sys.argv[1], 'r')
fi = open(sys.argv[2], 'a')
sep = "$-$"

totall2 = 0.0
totall1 = 0.0
l1_list = []
l2_list = []
for line in f:
  if sep in line:
    parts = line.split(sep)
    predicted = float(parts[1])
    actual = float(parts[3])
    diff = predicted - actual
    l1 = abs(diff)
    l2 = pow(diff, 2)
    totall2 += l2
    totall1 += l1
    l1_list.append(l1)
    l2_list.append(l2)
f.close()

l1s = np.array(l1_list)
l2s = np.array(l2_list)

l1_mean = np.mean(l1s)
l2_mean = np.mean(l2s)
l1_std = np.std(l1s)
l2_std = np.std(l2s)


fi.write(sys.argv[1]+"   total_l1="+str(totall1)+"    total_l2="+str(totall2)+"    avg_l1="+str(l1_mean)+"    avg_l2="+str(l2_mean)+"    std_l1="+str(l1_std)+"    std_l2="+str(l2_std)+"\n")
fi.close()
