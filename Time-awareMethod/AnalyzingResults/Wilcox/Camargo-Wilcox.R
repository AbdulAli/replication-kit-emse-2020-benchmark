#wilcoxon rank sum or Mann–Whitney U test
library(dplyr)
herbold<-read.csv(file="/home/hareem/EMSE-dataset/HerboldMethod/JURECZKO-CamargoCruz09.csv",header = TRUE)
data<-read.csv("/home/hareem/EMSE-dataset/output.csv",header=TRUE)

output<-summarise_at(group_by(data,technique,configuration,windowSize,time),vars(fscore,mcc,roc,gscore),funs(mean(.,na.rm=TRUE)))

C1<-output[output$configuration==1,]
C2<-output[output$configuration==2,]
C3<-output[output$configuration==3,]
C4<-output[output$configuration==4,]

cruzC1<-C1[C1$technique=="CamargoCruz09",]
cruzC2<-C2[C2$technique=="CamargoCruz09",]
cruzC3<-C3[C3$technique=="CamargoCruz09",]
cruzC4<-C4[C4$technique=="CamargoCruz09",]


#create vector for comparison by repeating mean  
test1<-rep(mean(herbold$fscore_DT),length(cruzC1$fscore))
test2<-rep(mean(herbold$fscore_DT),length(cruzC2$fscore))
test3<-rep(mean(herbold$fscore_DT),length(cruzC3$fscore))
test4<-rep(mean(herbold$fscore_DT),length(cruzC4$fscore))


res1<-wilcox.test(test1,cruzC1$fscore)
res2<-wilcox.test(test2,cruzC2$fscore)
res3<-wilcox.test(test3,cruzC3$fscore)
res4<-wilcox.test(test4,cruzC4$fscore)

#################################################### NEW RESULTS ######################################################
'cliffC4<-orddom(herbold$fscore_DT,cruzC4$fscore,alpha=0.01,paired=FALSE)
cliffC3<-orddom(herbold$fscore_DT,cruzC3$fscore,alpha=0.01,paired=FALSE)
cliffC2<-orddom(herbold$fscore_DT,cruzC2$fscore,alpha=0.01,paired=FALSE)
cliffC1<-orddom(herbold$fscore_DT,cruzC1$fscore,alpha=0.01,paired=FALSE)
