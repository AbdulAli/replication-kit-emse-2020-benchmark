amasaki = read.csv("HMBalancedHM/amasaki_header.csv")
ma = read.csv("HMBalancedHM/ma.csv")
nam = read.csv("HMBalancedHM/nam.csv")
camruz = read.csv("HMBalancedHM/cruz.csv")
watanabe = read.csv("HMBalancedHM/wat.csv")

amasaki$technique = c(rep("Amasaki", length(amasaki$version)))
ma$technique =c(rep("Ma", length(ma$version)))
nam$technique = c(rep("Nam", length(nam$version)))
camruz$technique = c(rep("Camruz", length(camruz$version)))
watanabe$technique = c(rep("Watanabe", length(watanabe$version)))

hm = data.frame(technique=(c(amasaki$technique,ma$technique,nam$technique,camruz$technique,watanabe$technique)),
                fscore=c(amasaki$fscore_DT,ma$fscore_DT,nam$fscore_DT,camruz$fscore_DT,watanabe$fscore_DT),
                mcc=c(amasaki$mcc_DT,ma$mcc_DT,nam$mcc_DT,camruz$mcc_DT,watanabe$mcc_DT),
                roc=c(amasaki$auc_DT,ma$auc_DT,nam$auc_DT,camruz$auc_DT,watanabe$auc_DT),
                gscore=c(amasaki$gscore_DT,ma$gscore_DT,nam$gscore_DT,camruz$gscore_DT,watanabe$gscore_DT))


hmResultMean<-summarise_at(group_by(hm,technique),vars(fscore,mcc,roc,gscore),funs(mean(.,na.rm=TRUE)))
hmResultSd<-summarise_at(group_by(hm,technique),vars(fscore,mcc,roc,gscore),funs(sd(.,na.rm=TRUE)))

tw = read.csv("herboldBalancedTimeWise.csv")

twResultMean<-summarise_at(group_by(tw,technique),vars(fscore,mcc,roc,gscore),funs(mean(.,na.rm=TRUE)))
twResultSd<-summarise_at(group_by(tw,technique),vars(fscore,mcc,roc,gscore),funs(sd(.,na.rm=TRUE)))

# result <- data.frame(type=c(rep("HM",length(hmResultMean$technique)),rep("TW",length(twResultMean$technique))),
#                      technique=c(c(hmResultMean$technique),c(twResultMean$technique)),
#                      meanFscore=c(hmResultMean$fscore,twResultMean$fscore),
#                      sdFscore=c(hmResultSd$fscore,twResultSd$fscore),
#                      meanMcc=c(hmResultMean$mcc,twResultMean$mcc),
#                      sdMCC=c(hmResultSd$mcc,twResultSd$mcc),
#                      meanROC=c(hmResultMean$roc,twResultMean$roc),
#                      sdROC=c(hmResultSd$roc,twResultSd$roc),
#                      meanGscore=c(hmResultMean$gscore,twResultMean$gscore),
#                      sdGscore=c(hmResultSd$gscore,twResultSd$gscore))


result <- data.frame(type=c(rep("HM",length(hmResultMean$technique)),rep("TW",length(twResultMean$technique))),
                     technique=c(c(hmResultMean$technique),c(twResultMean$technique)),
                     sdFscore=c(hmResultSd$fscore,twResultSd$fscore),
                     sdMCC=c(hmResultSd$mcc,twResultSd$mcc),
                     sdROC=c(hmResultSd$roc,twResultSd$roc),
                     sdGscore=c(hmResultSd$gscore,twResultSd$gscore))

result[result$technique==1,]$technique = "Amasaki15"
result[result$technique==2,]$technique = "CamargoCruz09"
result[result$technique==3,]$technique = "Ma12"
result[result$technique==4,]$technique = "Nam15"
result[result$technique==5,]$technique = "Watanabe08"

fscore <- tw[tw$technique=='Amasaki15',]$fscore
hmFscore <- result[result$type == 'HM' & result$technique == 'Amasaki15',]$meanFscore
wt <- wilcox.test(fscore,rep(hmFscore,length(fscore)))

fscore <- tw[tw$technique=='CamargoCruz09',]$fscore
hmFscore <- result[result$type == 'HM' & result$technique == 'CamargoCruz09',]$meanFscore
wt <- wilcox.test(fscore,rep(hmFscore,length(fscore)))

fscore <- tw[tw$technique=='Ma12',]$fscore
hmFscore <- result[result$type == 'HM' & result$technique == 'Ma12',]$meanFscore
wt <- wilcox.test(fscore,rep(hmFscore,length(fscore)))

fscore <- tw[tw$technique=='Nam15',]$fscore
hmFscore <- result[result$type == 'HM' & result$technique == 'Nam15',]$meanFscore
wt <- wilcox.test(fscore,rep(hmFscore,length(fscore)))

fscore <- tw[tw$technique=='Watanabe08',]$fscore
hmFscore <- result[result$type == 'HM' & result$technique == 'Watanabe08',]$meanFscore
wt <- wilcox.test(fscore,rep(hmFscore,length(fscore)))

