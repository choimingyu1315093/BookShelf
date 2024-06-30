package com.choisong.bookshelf.di

import com.choisong.bookshelf.network.*
import com.choisong.bookshelf.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //id체크
    @Singleton
    @Provides
    fun providePostIdCheckInstance(): PostIdCheckApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostIdCheckApi::class.java)
    }

    //nickname체크
    @Singleton
    @Provides
    fun providePostNicknameCheckInstance(): PostNicknameCheckApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostNicknameCheckApi::class.java)
    }

    //email체크
    @Singleton
    @Provides
    fun providePostEmailCheckInstance(): PostEmailCheckApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostEmailCheckApi::class.java)
    }

    //회원가입
    @Singleton
    @Provides
    fun providePostSignUpInstance(): PostSignUpApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostSignUpApi::class.java)
    }

    //sns회원가입
    @Singleton
    @Provides
    fun providePostSnsSignUpInstance(): PostSnsSignUpApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostSnsSignUpApi::class.java)
    }

    //로그인
    @Singleton
    @Provides
    fun providePostSignInInstance(): PostSignInApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostSignInApi::class.java)
    }

    //sns로그인
    @Singleton
    @Provides
    fun providePostSnsSignInInstance(): PostSnsSignInApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostSnsSignInApi::class.java)
    }

    //현재위치 업데이트
    @Singleton
    @Provides
    fun providePatchUserLocationInstance(): PatchUserLocationApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PatchUserLocationApi::class.java)
    }

    //나의프로필
    @Singleton
    @Provides
    fun provideGetMyProfileInstance(): GetMyProfileApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetMyProfileApi::class.java)
    }

    //닉네임 변경
    @Singleton
    @Provides
    fun providePatchNicknameInstance(): PatchNicknameApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PatchNicknameApi::class.java)
    }

    //한 줄 메시지 변경
    @Singleton
    @Provides
    fun providePatchDescriptionInstance(): PatchDescriptionApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PatchDescriptionApi::class.java)
    }

    //알람카운트
    @Singleton
    @Provides
    fun provideGetAlarmCountInstance(): GetAlarmCountApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetAlarmCountApi::class.java)
    }

    //알람리스트
    @Singleton
    @Provides
    fun provideGetAlarmListInstance(): GetAlarmListApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetAlarmListApi::class.java)
    }

    //알람 하나 삭제
    @Singleton
    @Provides
    fun provideDeleteAlarmInstance(): DeleteAlarmApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeleteAlarmApi::class.java)
    }

    //알림 전체 삭제
    @Singleton
    @Provides
    fun provideDeleteAllAlarmInstance(): DeleteAllAlarmApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeleteAllAlarmApi::class.java)
    }

    //아이디 찾기
    @Singleton
    @Provides
    fun providePostFindIdInstance(): PostFindIdApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostFindIdApi::class.java)
    }

    //비밀번호 찾기
    @Singleton
    @Provides
    fun providePostFindPasswordInstance(): PostFindPasswordApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostFindPasswordApi::class.java)
    }

    //비밀번호 변경
    @Singleton
    @Provides
    fun providePatchChangePasswordInstance(): PatchChangePasswordApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PatchChangePasswordApi::class.java)
    }

    //베스트셀러
    @Singleton
    @Provides
    fun provideGetBestsellerInstance(): GetBestsellerApi {
        return Retrofit.Builder()
            .baseUrl("http://www.aladin.co.kr/ttb/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetBestsellerApi::class.java)
    }

    //검색
    @Singleton
    @Provides
    fun provideGetSearchBookInstance(): GetSearchBookApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetSearchBookApi::class.java)
    }

    //검색결과 더보기
    @Singleton
    @Provides
    fun provideGetSearchBookMoreInstance(): GetSearchMoreBookApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetSearchMoreBookApi::class.java)
    }

    //내가 보유 중인 책 리스트
    @Singleton
    @Provides
    fun provideGetHaveBookInstance(): GetHaveBookApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetHaveBookApi::class.java)
    }

    //내가 보유 중인 책 등록
    @Singleton
    @Provides
    fun providePostAddHaveBookInstance(): PostAddHaveBookApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostAddHaveBookApi::class.java)
    }

    //내가 보유 중인 책 삭제
    @Singleton
    @Provides
    fun provideDeleteHaveBookInstance(): DeleteHaveBookApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeleteHaveBookApi::class.java)
    }

    //내가 읽고 싶은 책 리스트
    @Singleton
    @Provides
    fun provideGetWishBookInstance(): GetWishBookApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetWishBookApi::class.java)
    }

    //내가 읽고 싶은 책 등록
    @Singleton
    @Provides
    fun providePostAddWishBookInstance(): PostAddWishBookApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostAddWishBookApi::class.java)
    }

    //내가 읽고 싶은 책 삭제
    @Singleton
    @Provides
    fun provideDeleteWishBookInstance(): DeleteWishBookApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeleteWishBookApi::class.java)
    }

    //책 상세보기
    @Singleton
    @Provides
    fun provideGetBookDetailInstance(): GetBookDetailApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetBookDetailApi::class.java)
    }

    //평점달기
    @Singleton
    @Provides
    fun providePutUpdateCommentInstance(): PutUpdateCommentApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PutUpdateCommentApi::class.java)
    }

    //평점수정
    @Singleton
    @Provides
    fun providePostAddCommentInstance(): PostAddCommentApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostAddCommentApi::class.java)
    }

    //평점삭제
    @Singleton
    @Provides
    fun provideDeleteCommentInstance(): DeleteCommentApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeleteCommentApi::class.java)
    }

    //내가 읽고 싶은 책을 보유 중인 유저 검색
    @Singleton
    @Provides
    fun provideGetIWishBookHaveUserInstance(): GetIWishBookHaveUserApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetIWishBookHaveUserApi::class.java)
    }

    //프로필 가져오기
    @Singleton
    @Provides
    fun provideGetUserSettingInstance(): GetUserSettingApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetUserSettingApi::class.java)
    }

    //프로필 세팅
    @Singleton
    @Provides
    fun providePatchUserSettingInstance(): PatchUserSettingApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PatchUserSettingApi::class.java)
    }

    //탈퇴
    @Singleton
    @Provides
    fun provideDeleteUserInstance(): DeleteUserApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeleteUserApi::class.java)
    }

    //채팅방 만들기
    @Singleton
    @Provides
    fun providePostCreateChatroomInstance(): PostCreateChatroomApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostCreateChatroomApi::class.java)
    }

    //채팅방 상세보기
    @Singleton
    @Provides
    fun provideGetChatroomDetailInstance(): GetChatroomDetailApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetChatroomDetailApi::class.java)
    }

    //채팅 메세지 등록
    @Singleton
    @Provides
    fun providePostChatMessageInstance(): PostChatMessageApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostChatMessageApi::class.java)
    }

    //채팅방 나가기
    @Singleton
    @Provides
    fun providePatchChatroomOutInstance(): PatchChatroomOutApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PatchChatroomOutApi::class.java)
    }

    //채팅방 나가기
    @Singleton
    @Provides
    fun provideDeleteChatroomInstance(): DeleteChatroomApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeleteChatroomApi::class.java)
    }

    //채팅 목록 가져오기
    @Singleton
    @Provides
    fun provideGetChatroomListInstance(): GetChatroomListApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetChatroomListApi::class.java)
    }

    //교환권 구매
//    @Singleton
//    @Provides
//    fun providePatchTicketBuyInstance(): PatchTicketBuyApi {
//        return Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(PatchTicketBuyApi::class.java)
//    }

    //구매 내역 조회
    @Singleton
    @Provides
    fun provideGetTicketLogInstance(): GetTicketLogApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetTicketLogApi::class.java)
    }

    //보유중인책 등록
    @Singleton
    @Provides
    fun providePostMyBooksInstance(): PostMyBookApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostMyBookApi::class.java)
    }

    //프로필 활동 가져오기
    @Singleton
    @Provides
    fun provideGetProfileActive(): GetProfileActiveApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetProfileActiveApi::class.java)
    }

    //프로필 메모 가져오기
    @Singleton
    @Provides
    fun provideGetProfileMemo(): GetProfileMemoApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetProfileMemoApi::class.java)
    }

    //메모 등록
    @Singleton
    @Provides
    fun providePostAddMemo(): PostAddMemoApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostAddMemoApi::class.java)
    }

    //책 메모 가져오기
    @Singleton
    @Provides
    fun provideGetBookMemo(): GetBookMemoApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetBookMemoApi::class.java)
    }

    //티켓구매
    @Singleton
    @Provides
    fun providePatchBuyTicket(): PatchBuyTicketsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PatchBuyTicketsApi::class.java)
    }
}