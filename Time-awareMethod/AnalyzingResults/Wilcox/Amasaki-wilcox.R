#wilcoxon rank sum or Mann–Whitney U test
library(dplyr)
library(orddom)
herbold<-read.csv(file="/home/hareem/EMSE-dataset/HerboldMethod/JURECZKO-Amasaki15.csv",header = TRUE)
data<-read.csv("/home/hareem/EMSE-dataset/output.csv",header=TRUE)

output<-summarise_at(group_by(data,technique,configuration,windowSize,time),vars(fscore,mcc,roc,gscore),funs(mean(.,na.rm=TRUE)))

C1<-output[output$configuration==1,]
C2<-output[output$configuration==2,]
C3<-output[output$configuration==3,]
C4<-output[output$configuration==4,]

amaC1<-C1[C1$technique=="Amasaki15",]
amaC2<-C2[C2$technique=="Amasaki15",]
amaC3<-C3[C3$technique=="Amasaki15",]
amaC4<-C4[C4$technique=="Amasaki15",]

#make a vector by repeating mean values
test1<-rep(mean(herbold$fscore_DT),length(amaC1$fscore))
test2<-rep(mean(herbold$fscore_DT),length(amaC2$fscore))
test3<-rep(mean(herbold$fscore_DT),length(amaC3$fscore))
test4<-rep(mean(herbold$fscore_DT),length(amaC4$fscore))

res1<-wilcox.test(test1,amaC1$fscore)
res2<-wilcox.test(test2,amaC2$fscore)
res3<-wilcox.test(test3,amaC3$fscore)
res4<-wilcox.test(test4,amaC4$fscore)#wilcoxon rank sum or Mann–Whitney U test
library(dplyr)
library(orddom)
herbold<-read.csv(file="/home/hareem/EMSE-dataset/HerboldMethod/JURECZKO-Amasaki15.csv",header = TRUE)
data<-read.csv("/home/hareem/EMSE-dataset/output.csv",header=TRUE)

output<-summarise_at(group_by(data,technique,configuration,windowSize,time),vars(fscore,mcc,roc,gscore),funs(mean(.,na.rm=TRUE)))

C1<-output[output$configuration==1,]
C2<-output[output$configuration==2,]
C3<-output[output$configuration==3,]
C4<-output[output$configuration==4,]

amaC1<-C1[C1$technique=="Amasaki15",]
amaC2<-C2[C2$technique=="Amasaki15",]
amaC3<-C3[C3$technique=="Amasaki15",]
amaC4<-C4[C4$technique=="Amasaki15",]

#make a vector by repeating mean values
test1<-rep(mean(herbold$fscore_DT),length(amaC1$fscore))
test2<-rep(mean(herbold$fscore_DT),length(amaC2$fscore))
test3<-rep(mean(herbold$fscore_DT),length(amaC3$fscore))
test4<-rep(mean(herbold$fscore_DT),length(amaC4$fscore))

res1<-wilcox.test(test1,amaC1$fscore)
res2<-wilcox.test(test2,amaC2$fscore)
res3<-wilcox.test(test3,amaC3$fscore)
res4<-wilcox.test(test4,amaC4$fscore)

cliffC4<-orddom(test1,amaC4$fscore,alpha=0.01,paired=FALSE)
cliffC3<-orddom(test2,amaC3$fscore,alpha=0.01,paired=FALSE)
cliffC2<-orddom(test3,amaC2$fscore,alpha=0.01,paired=FALSE)
cliffC1<-orddom(test4,amaC1$fscore,alpha=0.01,paired=FALSE)

#################################################### NEW RESULTS ######################################################
'