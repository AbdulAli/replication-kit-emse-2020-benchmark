#wilcoxon rank sum or Mann–Whitney U test
library(dplyr)
herbold<-read.csv(file="/home/hareem/EMSE-dataset/HerboldMethod/JURECZKO-Watanabe08.csv",header = TRUE)
data<-read.csv("/home/hareem/EMSE-dataset/output.csv",header=TRUE)

output<-summarise_at(group_by(data,technique,configuration,windowSize,time),vars(fscore,mcc,roc, gscore),funs(mean(.,na.rm=TRUE)))

C1<-output[output$configuration==1,]
C2<-output[output$configuration==2,]
C3<-output[output$configuration==3,]
C4<-output[output$configuration==4,]

watC1<-C1[C1$technique=="Watanabe08",]
watC2<-C2[C2$technique=="Watanabe08",]
watC3<-C3[C3$technique=="Watanabe08",]
watC4<-C4[C4$technique=="Watanabe08",]

#create vector for comparison by repeating mean  
test1<-rep(mean(herbold$fscore_DT),length(watC1$fscore))
test2<-rep(mean(herbold$fscore_DT),length(watC2$fscore))
test3<-rep(mean(herbold$fscore_DT),length(watC3$fscore))
test4<-rep(mean(herbold$fscore_DT),length(watC4$fscore))

res1<-wilcox.test(test1,watC1$fscore)
res2<-wilcox.test(test2,watC2$fscore)
res3<-wilcox.test(test3,watC3$fscore)
res4<-wilcox.test(test4,watC4$fscore)

cliffC4<-orddom(test1,watC4$fscore,alpha=0.01,paired=FALSE)
cliffC3<-orddom(test2,watC3$fscore,alpha=0.01,paired=FALSE)
cliffC2<-orddom(test3,watC2$fscore,alpha=0.01,paired=FALSE)
cliffC1<-orddom(test4,watC1$fscore,alpha=0.01,paired=FALSE)

#################################################### NEW RESULTS ######################################################
