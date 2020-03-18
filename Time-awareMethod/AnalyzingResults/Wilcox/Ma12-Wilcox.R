#wilcoxon rank sum or Mann–Whitney U test
library(dplyr)
herbold<-read.csv(file="/home/hareem/EMSE-dataset/HerboldMethod/JURECZKO-Ma12.csv",header = TRUE)
data<-read.csv("/home/hareem/EMSE-dataset/output.csv",header=TRUE)

output<-summarise_at(group_by(data,technique,configuration,windowSize,time),vars(fscore,mcc,roc,gscore),funs(mean(.,na.rm=TRUE)))

C1<-output[output$configuration==1,]
C2<-output[output$configuration==2,]
C3<-output[output$configuration==3,]
C4<-output[output$configuration==4,]

maC1<-C1[C1$technique=="Ma12",]
maC2<-C2[C2$technique=="Ma12",]
maC3<-C3[C3$technique=="Ma12",]
maC4<-C4[C4$technique=="Ma12",]

#create vector for comparison by repeating mean  
test1<-rep(mean(herbold$fscore_DT),length(maC1$fscore))
test2<-rep(mean(herbold$fscore_DT),length(maC2$fscore))
test3<-rep(mean(herbold$fscore_DT),length(maC3$fscore))
test4<-rep(mean(herbold$fscore_DT),length(maC4$fscore))

res1<-wilcox.test(test1,maC1$fscore)
res2<-wilcox.test(test2,maC2$fscore)
res3<-wilcox.test(test3,maC3$fscore)
res4<-wilcox.test(test4,maC4$fscore)

cliffC4<-orddom(herbold$fscore_DT,maC4$fscore,alpha=0.01,paired=FALSE)
cliffC3<-orddom(herbold$fscore_DT,maC3$fscore,alpha=0.01,paired=FALSE)
cliffC2<-orddom(herbold$fscore_DT,maC2$fscore,alpha=0.01,paired=FALSE)
cliffC1<-orddom(herbold$fscore_DT,maC1$fscore,alpha=0.01,paired=FALSE)

#################################################### NEW RESULTS ######################################################

